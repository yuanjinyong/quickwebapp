/**
 * 
 */
package com.quickwebapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.quickwebapp.core.listener.ApplicationStartup;

/**
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.quickwebapp.**.controller.**", "com.quickwebapp.**.service.**" })
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
