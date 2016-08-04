package com.quickwebapp.usm.sys.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quickwebapp.framework.core.exception.BusinessException;
import com.quickwebapp.usm.sys.entity.RoleEntity;
import com.quickwebapp.usm.sys.security.OpenIdAuthenticationToken;
import com.quickwebapp.usm.sys.security.SecurityUser;
import com.quickwebapp.usm.sys.service.UserService;

@RestController
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    /**
     * 获取用户信息。
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/sys/user", method = RequestMethod.GET)
    public ResponseEntity<SecurityUser> getCurUser(Authentication token) {
        LOG.info("获取当前用户信息：{}", token.getPrincipal());

        SecurityUser curUser = null;
        if (token instanceof UsernamePasswordAuthenticationToken) {
            curUser = (SecurityUser) token.getPrincipal();
            if (curUser.getUser().isSuperAdmin()) {
                curUser.setMenu(userService.getMenuTree());
            } else {
                curUser.setMenu(userService.getMenuTree(curUser.getUser().getF_id()));
            }
        } else if (token instanceof OpenIdAuthenticationToken) {
            curUser = (SecurityUser) token.getPrincipal();
            curUser.setMenu(userService.getMenuTree(RoleEntity.ROLE_WEIXIN)); // WEIXIN角色的ID写死为2
        } else {
            throw new BusinessException("未知的授权类型[" + token.getClass().getName() + "]，请联系系统管理员！");
        }

        return new ResponseEntity<SecurityUser>(curUser, HttpStatus.OK);
    }
}
