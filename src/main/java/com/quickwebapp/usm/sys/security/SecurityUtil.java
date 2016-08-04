/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.quickwebapp.usm.sys.entity.UserEntity;

/**
 * @author 袁进勇
 *
 */
public final class SecurityUtil {
    /**
     * 获取系统当前操作员信息
     * 
     * @return 当前操作员信息
     */
    public static UserEntity getCurrentUser() {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        if (token instanceof UsernamePasswordAuthenticationToken) {
            if (token.getPrincipal() instanceof SecurityUser) {
                SecurityUser user = (SecurityUser) token.getPrincipal();
                return user.getUser();
            }
        }

        return null;
    }

    // /**
    // * 获取系统当前微信会员信息
    // *
    // * @return 当前微信会员信息
    // */
    // public static MemberEntity getCurrentMember() {
    // Authentication token = SecurityContextHolder.getContext().getAuthentication();
    // if (token instanceof OpenIdAuthenticationToken) {
    // if (token.getPrincipal() instanceof SecurityUser) {
    // SecurityUser user = (SecurityUser) token.getPrincipal();
    // return user.getMember();
    // }
    // }
    //
    // return null;
    // }
}
