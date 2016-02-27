/**
 * 
 */
package com.quickwebapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 *
 */
@ComponentScan(basePackages = { "com.pkpmjc.erp.**.controller.**", "com.pkpmjc.erp.**.service.**" })
@MapperScan(basePackages = { "com.pkpmjc.erp.**.mapper.**" })
@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}