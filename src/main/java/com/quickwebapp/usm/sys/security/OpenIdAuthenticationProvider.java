/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.alibaba.druid.support.json.JSONUtils;

/**
 * @author Administrator
 *
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(OpenIdAuthenticationProvider.class);

    @Override
    public boolean supports(Class<?> authentication) {
        return (OpenIdAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OpenIdAuthenticationToken token = (OpenIdAuthenticationToken) authentication;
        HttpGet request = new HttpGet(
                "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9504135e7dbe2999&secret=9229e5d3954ba97b63b367b45943a5bb&code="
                        + token.getCode() + "&grant_type=authorization_code");
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpResponse response = httpClient.execute(request);
            String responseContent = EntityUtils.toString(response.getEntity(), "UTF-8");
            @SuppressWarnings("unchecked")
            Map<String, Object> result = (Map<String, Object>) JSONUtils.parse(responseContent);
            Long errcode = result.get("errcode") == null ? 0L : Long.valueOf(result.get("errcode").toString());
            if (errcode != 0) {
                throw new BadCredentialsException((String) result.get("errmsg"));
            }

            String openId = (String) result.get("openid");
            LOG.info("openid is: " + openId);

            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("WeixinUser"));
            OpenIdAuthenticationToken authResult = new OpenIdAuthenticationToken(token.getCode(), authorities);
            authResult.setOpenid(openId);
            authResult.setAccessToken((String) result.get("access_token"));
            authResult.setExpiresIn((Integer) result.get("expires_in"));
            authResult.setAuthenticated(true);

            return authResult;
        } catch (IOException e) {
            throw new BadCredentialsException("调用URL地址[" + request.toString() + "]失败：", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                LOG.error("Close http client failed: ", e);
            }
        }
    }

}
