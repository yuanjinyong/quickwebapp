/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.quickwebapp.usm.sys.entity.MenuEntity;

/**
 * @author JohnYuan
 *
 */
public class SecurityUser extends UsernamePasswordAuthenticationToken {
    private static final long serialVersionUID = 1L;

    private MenuEntity menu; // 以ROOT为根节点构造的菜单树

    public SecurityUser(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public SecurityUser(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }
}
