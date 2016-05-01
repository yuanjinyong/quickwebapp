package com.quickwebapp.core.interceptor.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quickwebapp.core.entity.MapEntity;
import com.quickwebapp.core.utils.HelpUtil;

/**
 * 通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，重写sql语句实现物理分页。
 * 签名里要拦截的类型只能是接口。
 * 
 * @author JohnYuan
 *
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(PageInterceptor.class);
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    private String dialect;
    private String sqlIdRegex;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof RoutingStatementHandler) {
            MetaObject metaObject = getMetaObject((StatementHandler) invocation.getTarget());
            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();

            // 如果没有传入任何参数或者入参不是统一的MapEntity，则不进行翻页数据的处理
            if (parameterObject != null && parameterObject instanceof MapEntity) {
                MapEntity mapEntity = (MapEntity) parameterObject;
                // 要传入了pageSize且SqlId以“ListPage”结尾，才进行翻页数据的处理
                if (mapEntity.getPageSize() != null) {
                    MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
                    if (mappedStatement.getId().matches(this.sqlIdRegex)) {
                        processTotalCount((Connection) invocation.getArgs()[0], mappedStatement, boundSql);

                        String pageSql = generatePageSql(boundSql.getSql(), mapEntity);
                        metaObject.setValue("delegate.boundSql.sql", pageSql);

                        LOG.info("\n重写分页后的SQL如下：\n" + mappedStatement.getId() + "\n" + mappedStatement.getResource()
                                + "\n" + pageSql);
                    }
                }
            }
        }

        return invocation.proceed();
    }

    private MetaObject getMetaObject(StatementHandler statementHandler) {
        MetaObject metaObject = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
        while (metaObject.hasGetter("h")) {
            Object object = metaObject.getValue("h");
            metaObject = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
                    DEFAULT_REFLECTOR_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaObject.hasGetter("target")) {
            Object object = metaObject.getValue("target");
            metaObject = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
                    DEFAULT_REFLECTOR_FACTORY);
        }

        return metaObject;
    }

    private void processTotalCount(Connection connection, MappedStatement mappedStatement, BoundSql boundSql)
            throws Exception, SQLException {
        MapEntity parameterObject = (MapEntity) boundSql.getParameterObject();
        // 如果入参中已经传入了totalCount，则不需要再去查询出总数了
        if (parameterObject.getTotalCount() != null && parameterObject.getTotalCount() > 0) {
            return;
        }

        PreparedStatement countStmt = null;
        ResultSet resultSet = null;
        try {
            String countSql = generatCountSql(mappedStatement, boundSql);
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), parameterObject);

            setParameters(countStmt, mappedStatement, countBoundSql, parameterObject);

            resultSet = countStmt.executeQuery();
            if (resultSet.next()) {
                parameterObject.setTotalCount(resultSet.getInt(1));
                LOG.info("\n重写分页参数里的总页数：totalCount={}\n{}", parameterObject.getTotalCount(), countSql);
            }
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } finally {
                if (countStmt != null) {
                    countStmt.close();
                }
            }
        }
    }

    private String generatCountSql(MappedStatement mappedStatement, BoundSql boundSql) {
        String countSqlId = mappedStatement.getId().replaceAll(sqlIdRegex, "ListCount");
        if (mappedStatement.getConfiguration().hasStatement(countSqlId)) {
            MappedStatement countMappedStatement = mappedStatement.getConfiguration().getMappedStatement(countSqlId);

            return countMappedStatement.getBoundSql(boundSql.getParameterObject()).getSql();
        } else {
            return "SELECT COUNT(1) TOTAL_COUNT FROM (" + boundSql.getSql() + ") temp ";
        }
    }

    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value)
                                    .getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName
                                + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    /**
     * 根据数据库方言，生成特定的分页sql
     * 
     * @param sql
     * @param page
     * @return
     */
    private String generatePageSql(String sql, MapEntity mapEntity) {
        Integer pageSize = mapEntity.getPageSize();
        Integer currentPage = mapEntity.getCurrentPage();
        Integer beginRow = (currentPage == null || currentPage < 2) ? 0 : (currentPage - 1) * pageSize;

        String orderBy = mapEntity.getOrderBy();

        StringBuffer pageSql = new StringBuffer();
        if ("mysql".equals(dialect)) {
            pageSql.append(sql);
            if (!HelpUtil.isEmptyString(orderBy)) {
                pageSql.append(" \nORDER BY ").append(orderBy);
            }
            pageSql.append(" \n LIMIT " + beginRow + "," + pageSize);
        } else if ("oracle".equals(dialect)) {
            pageSql.append("SELECT * FROM (SELECT TMP_TB.*, ROWNUM ROW_ID FROM (\n");
            pageSql.append(sql);
            if (!HelpUtil.isEmptyString(orderBy)) {
                pageSql.append(" \nORDER BY ").append(orderBy);
            }
            pageSql.append("\n) AS TMP_TB WHERE ROWNUM <= ").append(beginRow + pageSize).append(") WHERE ROW_ID > ")
                    .append(beginRow);
        }

        return pageSql.toString();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof RoutingStatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        this.dialect = properties.getProperty("dialect");
        this.sqlIdRegex = properties.getProperty("sqlIdRegex");
    }
}
