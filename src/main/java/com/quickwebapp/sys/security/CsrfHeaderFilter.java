/**
 * 
 */
package com.quickwebapp.sys.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * Spring Security’s built-in CSRF protection has kicked in to prevent us from shooting ourselves in the foot. All it
 * wants is a token sent to it in a header called “X-CSRF”. The value of the CSRF token was available server side in the
 * HttpRequest attributes from the initial request that loaded the home page. To get it to the client we could render it
 * using a dynamic HTML page on the server, or expose it via a custom endpoint, or else we could send it as a cookie.
 * The last choice is the best because Angular has built in support for CSRF (which it calls “XSRF”) based on cookies.
 * 
 * @author JohnYuan
 *
 */
public class CsrfHeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Angular wants the cookie name to be “XSRF-TOKEN” and Spring Security provides it as a request attribute, so
        // we just need to transfer the value from a request attribute to a cookie:
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrf != null) {
            Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
            String token = csrf.getToken();
            if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                cookie = new Cookie("XSRF-TOKEN", token);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        filterChain.doFilter(request, response);
    }
}