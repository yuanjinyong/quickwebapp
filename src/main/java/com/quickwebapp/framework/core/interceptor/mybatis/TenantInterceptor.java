package com.quickwebapp.framework.core.interceptor.mybatis;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.usm.sys.entity.UserEntity;
import com.quickwebapp.usm.sys.security.SecurityUtil;

/**
 * 通过拦截<code>Executor</code>的<code>query</code>方法，重写查询参数，实现租户数据隔离。
 * 签名里要拦截的类型只能是接口。
 * 
 * @author JohnYuan
 *
 */
@Intercepts({ @Signature(type = Executor.class, method = "query",
        args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class TenantInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(TenantInterceptor.class);

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();

        if (args[1] != null && args[1] instanceof MapEntity) {
            MapEntity mapEntity = (MapEntity) args[1];
            UserEntity user = SecurityUtil.getCurrentUser();
            if (user != null && !user.isSuperAdmin()) {
                mapEntity.put("f_tenant_cid", user.getF_tenant_cid());
                if (!mapEntity.containsKey("f_tenant_cid_in")) {
                    mapEntity.put("f_tenant_cid_in", "1," + user.getF_tenant_cid());
                }
                mapEntity.put("f_tenant_bid", user.getF_tenant_bid());
                if (!mapEntity.containsKey("f_tenant_bid_in")) {
                    mapEntity.put("f_tenant_bid_in", "1," + user.getF_tenant_bid());
                }
                LOG.info("f_tenant_cid: {}, f_tenant_bid: {}, f_tenant_cid_in: {}, f_tenant_bid_in: {}",
                        mapEntity.get("f_tenant_cid"), mapEntity.get("f_tenant_bid"), mapEntity.get("f_tenant_cid_in"),
                        mapEntity.get("f_tenant_bid_in"));
            } else {
                LOG.warn("f_tenant_cid: null, f_tenant_bid: null, f_tenant_cid_in: null, f_tenant_bid_in: null");
            }
        }

        return invocation.proceed();
    }
}
