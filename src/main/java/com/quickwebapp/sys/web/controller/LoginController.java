package com.quickwebapp.sys.web.controller;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quickwebapp.sys.security.CsrfHeaderFilter;
import com.quickwebapp.sys.security.SecurityUser;
import com.quickwebapp.sys.service.MenuService;

@RestController
public class LoginController {
    @Resource
    private MenuService menuService;

    /**
     * 获取用户信息，用于首次打开主页时判断是否需要登录。
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<SecurityUser> user(HttpServletRequest request) {
        return new ResponseEntity<SecurityUser>((SecurityUser) request.getSession().getAttribute("curUser"),
                HttpStatus.OK);
    }

    /**
     * 登录认证
     * 
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/authenticate")
    public Principal authenticate(Principal user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
        SecurityUser curUser = new SecurityUser(token.getPrincipal(), token.getCredentials());
        curUser.setMenu(menuService.getMenuTree("ROOT", true));

        request.getSession().setAttribute("curUser", curUser);

        return curUser;
    }

    @Configuration
    @EnableWebSecurity
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity web) throws Exception {
            // Spring Security should completely ignore URLs
            web.ignoring().antMatchers("/", "/webjars/**", "/weblibs/**", "/app/**/*.css", "/app/**/*.js",
                    "/index.html", "/app/sys/login/*.html");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            // enable in memory based authentication with a user named "user" and "admin"
            auth.inMemoryAuthentication().withUser("user").password("pkpm").roles("USER").and().withUser("admin")
                    .password("pkpm").roles("USER", "ADMIN");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic();
            // 这里把登录和退出后要跳转的URL都改成根路径，让angular在前台来做跳转控制。
            // enable form based log in, and set permitAll for all URLs associated with Form Login
            http.formLogin().permitAll().loginPage("/");
            http.logout().logoutSuccessUrl("/");

            // 配置不需要登录即可访问的URL。
            http.authorizeRequests().antMatchers("/user", "/authenticate").permitAll();
            // 其他URL地址都需要登录
            http.authorizeRequests().anyRequest().authenticated();
            // TODO 需要等系统启动后根据数据库中配置URL访问权来刷新。

            // 添加一个过滤器，用于Spring Security和angular之间某些属性名的转换
            http.addFilterAfter(new CsrfHeaderFilter(), SessionManagementFilter.class).csrf()
                    .csrfTokenRepository(csrfTokenRepository());
        }

        // Tell Spring Security to expect the CSRF token in the format that Angular wants to send it back (a header
        // called “X-XRSF-TOKEN” instead of the default “X-CSRF-TOKEN”).
        private CsrfTokenRepository csrfTokenRepository() {
            HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
            repository.setHeaderName("X-XSRF-TOKEN");
            return repository;
        }
    }
}
