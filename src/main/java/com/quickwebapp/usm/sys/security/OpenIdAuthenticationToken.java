/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author 袁进勇
 *
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = -3287040854242762707L;
    private final String authorizationCode;
    private final String openid;
    private final Object principal;
    private String accessToken;
    private Integer expiresIn; // access_token接口调用凭证超时时间，单位（秒）

    public OpenIdAuthenticationToken(String authorizationCode, String openid) {
        super(null);
        super.setAuthenticated(false);
        this.authorizationCode = authorizationCode;
        this.openid = openid;
        this.principal = null;
    }

    public OpenIdAuthenticationToken(String authorizationCode, String openid, Object principal,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true); // must use super, as we override
        this.authorizationCode = authorizationCode;
        this.openid = openid;
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return authorizationCode;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public String getOpenid() {
        return openid;
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
}
