/**
 * 
 */
package com.quickwebapp.core.listener;

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

import com.quickwebapp.sys.entity.UrlEntity;
import com.quickwebapp.sys.service.UrlService;

/**
 * @author JohnYuan
 *
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    }

    private void refreshUrlAndRight(ApplicationContext ac) {
        // 是否启用新的URL权限控制，1启用、2不启用。
        // if (MapUtils.getInteger(HelpUtils.getSysSet(), "ENABLE_NEW_URL_RIGHT", 2) == 2) {
        logger.info("====启用新的URL权限控制，加载读取URL和权限控制信息。");
        long startTime = System.currentTimeMillis();
        updateRequestMapping(ac);
        // shiroService.updateFilterChains();
        logger.info("====URL和权限控制信息加载完成，耗时：{}ms。", (System.currentTimeMillis() - startTime));
        // } else {
        // shiroService.updateShiroFilterChains();
        // }
    }

    private void updateRequestMapping(ApplicationContext ac) {
        logger.info("========更新t_sys_url开始。");
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) ac
                .getBean("requestMappingHandlerMapping");
        UrlService urlService = (UrlService) ac.getBean("urlService");

        List<UrlEntity> entityList = new ArrayList<UrlEntity>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            entityList.add(buildUrlEntity(entry.getKey(), entry.getValue()));
        }
        urlService.updateAutoEntities(entityList);

        logger.info("========更新t_sys_url完成。");
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
        entity.setF_log(true);
        entity.setF_auto(true);
        
        String id = DigestUtils.md5Hex(entity.getF_handler_method());
        // String id = DigestUtils.md5Hex(entity.getF_patterns() + entity.getF_methods() + entity.getF_params()
        // + entity.getF_headers() + entity.getF_consumes() + entity.getF_produces() + entity.getF_custom());
        entity.setF_id(id);

        return entity;
    }
}
