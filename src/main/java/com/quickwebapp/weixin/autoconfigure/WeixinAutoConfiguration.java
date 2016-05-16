/**
 * 
 */
package com.quickwebapp.weixin.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.quickwebapp.weixin.http.client.WeixinClient;
import com.quickwebapp.weixin.service.EventMessageService;
import com.quickwebapp.weixin.service.MessageService;
import com.quickwebapp.weixin.service.MeunService;
import com.quickwebapp.weixin.service.impl.DefaultEventMessageServiceImpl;
import com.quickwebapp.weixin.service.impl.DefaultMessageServiceImpl;
import com.quickwebapp.weixin.service.impl.DefaultMeunServiceImpl;

/**
 * 微信相关的自动化配置
 * 
 * @author 袁进勇
 *
 */
@Configuration
@EnableConfigurationProperties(WeixinProperties.class)
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
public class WeixinAutoConfiguration implements ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(WeixinAutoConfiguration.class);

    @Autowired
    private WeixinProperties properties;
    private ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }

    @Bean
    public WeixinClient weixinClient() {
        LOG.info("微信服务器的URL地址为：{}", properties.getUrl());
        LOG.info("获取Access Token的URL地址：{}", properties.getAccessTokenUrl());
        LOG.info("获取用户信息的URL地址：{}", properties.getUserInfoUrl());

        WeixinClient weixinHttpClient = new WeixinClient();
        WeixinClient.setProperties(properties);

        return weixinHttpClient;
    }

    @Bean
    @ConditionalOnMissingBean(value = EventMessageService.class)
    public EventMessageService eventService() {
        return new DefaultEventMessageServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(value = MessageService.class)
    public MessageService messageService() {
        return new DefaultMessageServiceImpl();
    }

    @Bean(name = "meunService")
    @ConditionalOnMissingBean(name = "meunService")
    public MeunService meunService() {
        WeixinClient weixinClient = ac.getAutowireCapableBeanFactory().getBean("weixinClient", WeixinClient.class);
        DefaultMeunServiceImpl defaultMeunService = new DefaultMeunServiceImpl();
        defaultMeunService.setWeixinClient(weixinClient);
        return defaultMeunService;
    }
}
