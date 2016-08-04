/**
 * 
 */
package com.quickwebapp.framework.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.quickwebapp.weixin.http.client.WeixinClient;
import com.quickwebapp.weixin.service.MeunService;

/**
 * @author 袁进勇
 *
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationStartup.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext ac = event.getApplicationContext();
        // 在web 项目中（spring mvc），系统会存在两个容器，一个是root application context ,另一个是我们自己的 projectName-servlet context
        // 这种情况下，就会造成onApplicationEvent方法被执行两次。为了避免上面提到的问题，我们可以只在root application
        // context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理。
        if (ac.getParent() != null) {
            return;
        }

        LOG.info("Application context加载完成。");

        MeunService meunService = ac.getBean(WeixinClient.getProperties().getMeunService(), MeunService.class);
        meunService.createMenu();
    }
}
