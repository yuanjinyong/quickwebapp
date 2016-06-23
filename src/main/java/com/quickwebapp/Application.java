/**
 * 
 */
package com.quickwebapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.quickwebapp.framework.core.listener.ApplicationStartup;
import com.quickwebapp.usm.sys.security.SecurityConfiguration;

/**
 * @author 袁进勇
 *
 */
@SpringBootApplication(scanBasePackages = { "com.quickwebapp.**.controller.**", "com.quickwebapp.**.service.**" })
@MapperScan(basePackages = { "com.quickwebapp.**.mapper.**" })
public class Application {
    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new SecurityConfiguration();
    }

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
