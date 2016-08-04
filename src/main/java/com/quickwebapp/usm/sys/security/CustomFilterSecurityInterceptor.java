/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author 袁进勇
 *
 */
public class CustomFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    private static final String FILTER_APPLIED = "__spring_security_url_authorities_applied";
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    private List<RequestMatcher> ignoredMatchers = new ArrayList<RequestMatcher>();

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        if (fi.getRequest().getAttribute(FILTER_APPLIED) != null) {
            // ensure that filter is only applied once per request
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }

        fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);

        boolean isIgnored = false;
        for (RequestMatcher matcher : ignoredMatchers) {
            if (matcher.matches(fi.getRequest())) {
                isIgnored = true;
                break;
            }
        }

        if (isIgnored) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } else {
            InterceptorStatusToken token = super.beforeInvocation(fi);

            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    public void setIgnoredMatchers(String... antPatterns) {
        for (String pattern : antPatterns) {
            ignoredMatchers.add(new AntPathRequestMatcher(pattern));
        }
    }
}
