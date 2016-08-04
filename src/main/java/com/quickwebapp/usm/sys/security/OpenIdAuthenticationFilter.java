/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 如果是从微信重定向过来的，则获取用户的OpenId写入到
 * 
 * @author 袁进勇
 *
 */
public class OpenIdAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(OpenIdAuthenticationFilter.class);

    // private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new
    // WebAuthenticationDetailsSource();
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationManager authenticationManager;

    public OpenIdAuthenticationFilter(AuthenticationManager authenticationManager,
            AuthenticationEntryPoint authenticationEntryPoint) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null");
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String weixinCode = request.getHeader("Weixin-Code");
        String openid = request.getHeader("Open-ID");
        if ((weixinCode == null || weixinCode.trim().length() == 0)
                && (openid == null || openid.trim().length() == 0)) {
            chain.doFilter(request, response);
            return;
        }
        weixinCode = weixinCode == null ? null : new String(Base64.decode(weixinCode.getBytes("UTF-8")), "UTF-8");
        openid = openid == null ? null : new String(Base64.decode(openid.getBytes("UTF-8")), "UTF-8");
        LOG.debug("OpenId Authorization header found for Weixin-Code '" + weixinCode + "'");

        if (authenticationIsRequired(weixinCode, openid)) {
            OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(weixinCode, openid);

            try {
                // authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
                Authentication authResult = authenticationManager.authenticate(authRequest);

                LOG.debug("Authentication success: " + authResult);

                SecurityContextHolder.getContext().setAuthentication(authResult);
            } catch (AuthenticationException failed) {
                SecurityContextHolder.clearContext();
                LOG.debug("Authentication request for failed: " + failed);
                authenticationEntryPoint.commence(request, response, failed);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean authenticationIsRequired(String weixinCode, String openid) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }

        if (existingAuth instanceof OpenIdAuthenticationToken) {
            if (weixinCode != null && !weixinCode.equals(((OpenIdAuthenticationToken) existingAuth).getAuthorizationCode())) {
                return true;
            }

            if (openid != null && !openid.equals(((OpenIdAuthenticationToken) existingAuth).getOpenid())) {
                return true;
            }
        }

        return false;
    }
}
