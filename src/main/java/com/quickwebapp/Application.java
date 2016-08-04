/**
 * 
 */
package com.quickwebapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.quickwebapp.framework.core.listener.ApplicationStartup;

/**
 * @author 袁进勇
 *
 */
@SpringBootApplication(scanBasePackages = { "com.quickwebapp.**.controller.**", "com.quickwebapp.**.service.**",
        "com.quickwebapp.**.security.**" })
@MapperScan(basePackages = { "com.quickwebapp.**.mapper.**" })
public class Application {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // SpringApplication.run(Application.class, args);
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }
}
