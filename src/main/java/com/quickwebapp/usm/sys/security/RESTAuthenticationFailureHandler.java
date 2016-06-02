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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author 袁進勇
 *
 */
@Component
public class RESTAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RESTAuthenticationFailureHandler.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler#onAuthenticationFailure(
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        LOG.info("登录失败");

        super.onAuthenticationFailure(request, response, exception);
    }
}
