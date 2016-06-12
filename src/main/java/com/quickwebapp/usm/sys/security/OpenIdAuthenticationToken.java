/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Administrator
 *
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = -3287040854242762707L;
    private final String code;
    private String openid;
    private String accessToken;
    private Integer expiresIn; // access_token接口调用凭证超时时间，单位（秒）

    public OpenIdAuthenticationToken(String code) {
        super(null);
        this.code = code;
        setAuthenticated(false);
    }

    public OpenIdAuthenticationToken(String code, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.code = code;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return openid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getCode() {
        return code;
    }

}
