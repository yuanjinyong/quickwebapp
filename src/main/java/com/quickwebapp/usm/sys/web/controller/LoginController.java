package com.quickwebapp.usm.sys.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
     * 获取用户信息。
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/sys/user", method = RequestMethod.GET)
    public ResponseEntity<SecurityUser> getCurUser(Authentication token) {
        LOG.info("获取用户信息：{}", token.getPrincipal());
        SecurityUser curUser = new SecurityUser(token.getPrincipal(), null);
        curUser.setMenu(menuService.getMenuTree("ROOT", true));

        return new ResponseEntity<SecurityUser>(curUser, HttpStatus.OK);
    }
}
