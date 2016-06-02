package com.quickwebapp.usm.sys.web.controller;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quickwebapp.usm.sys.security.SecurityUser;
import com.quickwebapp.usm.sys.service.MenuService;

@RestController
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

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
        LOG.info("登录认证");
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
        SecurityUser curUser = new SecurityUser(token.getPrincipal(), token.getCredentials());
        curUser.setMenu(menuService.getMenuTree("ROOT", true));

        request.getSession().setAttribute("curUser", curUser);

        return curUser;
    }
}
