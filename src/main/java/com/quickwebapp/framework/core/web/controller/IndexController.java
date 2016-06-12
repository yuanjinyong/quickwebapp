/**
 * 
 */
package com.quickwebapp.framework.core.web.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页面的控制器
 * 
 * @author 袁进勇
 *
 */
@Controller
public class IndexController {
    private static final String PAGE_PATH = "index";
    private static final Long TIMESTAMP = new Date().getTime();

    // 从 application.properties 中读取配置，如取不到默认值为false
    @Value("${application.dev:false}")
    private boolean dev = false;

    @RequestMapping(value = "/")
    public String index(Map<String, Object> model) {
        model.put("dev", dev);
        model.put("timestamp", dev ? new Date().getTime() : TIMESTAMP);

        // 直接返回字符串，框架默认会取 spring.view.prefix目录下的 (index拼接spring.view.suffix)页面。本例为 /WEB-INF/jsp/index.jsp
        return PAGE_PATH;
    }
}
