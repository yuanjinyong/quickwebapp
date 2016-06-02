/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 *
 */
@Component
public class RESTLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RESTLogoutSuccessHandler.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler#onLogoutSuccess(javax.
     * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LOG.info("退出成功");
    }
}
