/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;

/**
 * @author 袁进勇
 *
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private OpenIdAuthenticationProvider openIdAuthenticationProvider;
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    @Qualifier("customInvocationSecurityMetadataSourceService")
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(daoAuthenticationProvider());

        auth.authenticationProvider(openIdAuthenticationProvider);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Spring Security should completely ignore URLs
        web.ignoring().antMatchers("/app/**", "/template/**", "/webjars/**", "/weblibs/**", "/index*.*",
                "/WEB-INF/jsp/**", "/", "/api/weixin/**", "/error");
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

        // 微信跳转过来的自动登录
        http.addFilterAfter(new OpenIdAuthenticationFilter(authenticationManager(), unauthorizedEntryPoint()),
                BasicAuthenticationFilter.class);

        // 添加自定义的过滤器 放在FILTER_SECURITY_INTERCEPTOR之前有效，在数据库中为访问URL所需要的菜单权限
        http.addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class);

        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests() //
                .antMatchers("/logout").anonymous() // 配置不需要登录即可访问的URL。
                // .anyRequest().denyAll(); // 其他URL地址不能访问
                .anyRequest().authenticated(); // 其他URL地址都需要登录

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

    @Bean
    public CustomFilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {
        CustomFilterSecurityInterceptor securityInterceptor = new CustomFilterSecurityInterceptor();
        // 配置不进行权限控制的URL，即不登陆也可以访问的URL
        securityInterceptor.setIgnoredMatchers("/app/**", "/template/**", "/webjars/**", "/weblibs/**", "/index*.*",
                "/WEB-INF/jsp/**", "/", "/api/weixin/**", "/error", "/logout", "/api/sys/user");
        securityInterceptor.setSecurityMetadataSource(securityMetadataSource);
        securityInterceptor.setAccessDecisionManager(new CustomAccessDecisionManager());
        securityInterceptor.setAuthenticationManager(authenticationManager());
        securityInterceptor.afterPropertiesSet();
        return securityInterceptor;
    }

    // Tell Spring Security to expect the CSRF token in the format that Angular wants to send it back (a header
    // called “X-XRSF-TOKEN” instead of the default “X-CSRF-TOKEN”).
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
