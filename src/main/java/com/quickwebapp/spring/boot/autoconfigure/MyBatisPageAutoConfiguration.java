/**
 * 
 */
package com.quickwebapp.spring.boot.autoconfigure;

import java.util.Properties;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.quickwebapp.framework.core.interceptor.mybatis.PageInterceptor;

/**
 * @author JohnYuan
 *
 */
@Configuration
@ConditionalOnClass({ PageInterceptor.class })
@EnableConfigurationProperties(MybatisPageProperties.class)
@AutoConfigureBefore(MybatisAutoConfiguration.class)
public class MyBatisPageAutoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(MyBatisPageAutoConfiguration.class);

    @Autowired
    private MybatisPageProperties properties;

    /**
     * 分页插件
     * 
     * @return
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        LOG.info("========注册MyBatis分页插件PageInterceptor========");
        PageInterceptor pageInterceptor = new PageInterceptor();

        Properties p = new Properties();
        p.setProperty("dialect", properties.getDialect());
        p.setProperty("sqlIdRegex", properties.getSqlIdRegex());
        pageInterceptor.setProperties(p);

        return pageInterceptor;
    }
}
