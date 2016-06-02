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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 *
 */
@Component
public class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOG = LoggerFactory.getLogger(RESTLogoutSuccessHandler.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.AuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        LOG.warn("访问URL地址{}需要先认证。", request.getRequestURI().substring(request.getContextPath().length()));

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
