/**
 * 
 */
package com.quickwebapp.spring.boot.autoconfigure;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author JohnYuan
 *
 */
@ConfigurationProperties(prefix = MybatisPageProperties.PREFIX)
public class MybatisPageProperties {
    public static final String PREFIX = "mybatis.page";

    /**
     * 数据库方言（类型）
     */
    private String dialect;

    /**
     * 应用分页插件的SQL语句的ID（正则表达式匹配）
     */
    private String sqlIdRegex;

    @PostConstruct
    public void init() {
        if (dialect == null) {
            dialect = "mysql";
        }
        if (sqlIdRegex == null) {
            sqlIdRegex = ".*ListPage$";
        }
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getSqlIdRegex() {
        return sqlIdRegex;
    }

    public void setSqlIdRegex(String sqlIdRegex) {
        this.sqlIdRegex = sqlIdRegex;
    }
}
