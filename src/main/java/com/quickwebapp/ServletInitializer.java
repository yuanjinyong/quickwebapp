/**
 * 
 */
package com.quickwebapp;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import com.quickwebapp.framework.core.listener.ApplicationStartup;

/**
 * @author Administrator
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.boot.context.web.SpringBootServletInitializer#configure(org.springframework.boot.builder.
     * SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.application().addListeners(new ApplicationStartup());
        return builder.sources(Application.class);
    }
}
