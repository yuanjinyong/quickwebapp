/**
 * 
 */
package com.quickwebapp.framework.core.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.quickwebapp.usm.sys.entity.UrlEntity;
import com.quickwebapp.usm.sys.service.UrlService;

/**
 * @author JohnYuan
 *
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationStartup.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 在web 项目中（spring mvc），系统会存在两个容器，一个是root application context ,另一个是我们自己的 projectName-servlet context
        // 这种情况下，就会造成onApplicationEvent方法被执行两次。为了避免上面提到的问题，我们可以只在root application
        // context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理。
        if (event.getApplicationContext().getParent() != null) {
            return;
        }

        // 当spring容器初始化完成后就会执行该方法。
        // if (MapUtils.getInteger(HelpUtils.getSysSet(), "ASYNC_LOAD_DATA", 2) == 1) { // 是否异步加载，默认为同步
        // new Thread(new Runnable() {
        // public void run() {
        // refreshUrlAndRight(event.getApplicationContext());
        // }
        // }).start();
        // } else {
        refreshUrlAndRight(event.getApplicationContext());
        // }
        // public static void main(null);
        // new MeunClient().createMenu();
    }

    private void refreshUrlAndRight(ApplicationContext ac) {
        LOG.info("========更新t_sys_url开始========");

        long startTime = System.currentTimeMillis();
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) ac
                .getBean("requestMappingHandlerMapping");
        UrlService urlService = (UrlService) ac.getBean("urlService");

        List<UrlEntity> entityList = new ArrayList<UrlEntity>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            entityList.add(buildUrlEntity(entry.getKey(), entry.getValue()));
        }
        urlService.updateAutoEntities(entityList);

        LOG.info("========更新t_sys_url完成，耗时：{}ms========", (System.currentTimeMillis() - startTime));
    }

    private UrlEntity buildUrlEntity(RequestMappingInfo mapping, HandlerMethod method) {
        UrlEntity entity = new UrlEntity();
        entity.setF_url(mapping.getPatternsCondition().getPatterns().iterator().next().substring(1));
        entity.setF_description("d");
        entity.setF_patterns(mapping.getPatternsCondition().toString());
        entity.setF_methods(mapping.getMethodsCondition().toString());
        entity.setF_params(mapping.getParamsCondition().toString());
        entity.setF_headers(mapping.getHeadersCondition().toString());
        entity.setF_consumes(mapping.getConsumesCondition().toString());
        entity.setF_produces(mapping.getProducesCondition().toString());
        entity.setF_custom(mapping.getCustomCondition() == null ? "[]" : mapping.getCustomCondition().toString());
        entity.setF_handler_method(method.getMethod().toString());
        entity.setF_log(0);
        entity.setF_auto(1);

        entity.setF_id(
                DigestUtils.md5Hex(new StringBuffer().append(entity.getF_patterns()).append(entity.getF_methods())
                        .append(entity.getF_params()).append(entity.getF_headers()).append(entity.getF_consumes())
                        .append(entity.getF_produces()).append(entity.getF_custom()).toString()));

        return entity;
    }
}
