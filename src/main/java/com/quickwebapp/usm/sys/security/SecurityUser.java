/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.quickwebapp.framework.core.utils.HelpUtil;
import com.quickwebapp.usm.sys.entity.MenuEntity;
import com.quickwebapp.usm.sys.entity.UserEntity;

/**
 * @author 袁进勇
 *
 */
public class SecurityUser extends User {
    private static final long serialVersionUID = -106245041227776233L;

    private UserEntity user; // 系统操作员
    // private MemberEntity member; // 微信会员信息
    private MenuEntity menu; // 以ROOT为根节点构造的菜单树

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getName() {
        // if (member != null) {
        // if (!HelpUtil.isEmptyString(member.getF_name())) {
        // return member.getF_name();
        // }
        // return member.getF_nickname();
        // }
        if (user != null) {
            if (!HelpUtil.isEmptyString(user.getF_name())) {
                return user.getF_name();
            }
            return user.getF_account();
        }

        return super.getUsername();
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    // public MemberEntity getMember() {
    // return member;
    // }
    //
    // public void setMember(MemberEntity member) {
    // this.member = member;
    // }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }
}
