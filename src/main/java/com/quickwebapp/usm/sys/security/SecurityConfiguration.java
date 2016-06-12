/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;

/**
 * @author Administrator
 *
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Spring Security should completely ignore URLs
        web.ignoring().antMatchers("/", "/webjars/**", "/weblibs/**", "/app/**", "/template/**", "/WEB-INF/jsp/**",
                "/api/weixin/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new OpenIdAuthenticationProvider());

        // enable in memory based authentication with a user named "user" and "admin"
        auth.inMemoryAuthentication().withUser("user").password("pkpm").roles("USER").and().withUser("admin")
                .password("pkpm").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        // 因为采用ajax请求，这里把未认证、登录和退出后的自动跳转URL覆写掉，页面的跳转在前台交给angular来处理。
        http.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());
        // http.formLogin() //
        // .successHandler(new RESTAuthenticationSuccessHandler())
        // .failureHandler(new RESTAuthenticationFailureHandler());
        http.logout().logoutSuccessHandler(new RESTLogoutSuccessHandler());

        http.authorizeRequests() //
                // .antMatchers("/authenticate").permitAll() // 配置不需要登录即可访问的URL。
                .anyRequest().authenticated(); // 其他URL地址都需要登录
        // TODO 需要等系统启动后根据数据库中配置URL访问权来刷新。
        http.addFilterAfter(new OpenIdAuthenticationFilter(authenticationManager(), unauthorizedEntryPoint()),
                BasicAuthenticationFilter.class);

        // 添加一个过滤器，用于Spring Security和angular之间某些属性名的转换
        // 注意：CsrfHeaderFilter过滤器的执行顺序需要放到SessionManagementFilter之后，
        // 否则在登录后不进行任何操作而直接退出时会报“Invalid CSRF Token was found on the request parameter '_csrf' or header
        // 'X-XSRF-TOKEN'.”或“Invalid CSRF Token 'c6ba56f4-96a5-437b-8872-a9aaaa4b8f98' was found on the request
        // parameter '_csrf' or header 'X-XSRF-TOKEN'.”。
        http.addFilterAfter(new CsrfHeaderFilter(), SessionManagementFilter.class) //
                .csrf().csrfTokenRepository(csrfTokenRepository());
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return new RESTAuthenticationEntryPoint();
    }

    // Tell Spring Security to expect the CSRF token in the format that Angular wants to send it back (a header
    // called “X-XRSF-TOKEN” instead of the default “X-CSRF-TOKEN”).
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
