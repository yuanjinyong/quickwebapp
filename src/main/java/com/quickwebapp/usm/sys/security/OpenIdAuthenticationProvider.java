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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.json.JSONUtils;
import com.quickwebapp.framework.core.entity.MapEntity;
import com.quickwebapp.framework.core.utils.HelpUtil;
import com.quickwebapp.usm.sys.entity.RoleEntity;
import com.quickwebapp.usm.sys.mapper.RoleMapper;
import com.quickwebapp.usm.sys.mapper.RoleMenuMapper;

/**
 * @author 袁进勇
 *
 */
@Component
public class OpenIdAuthenticationProvider implements AuthenticationProvider, InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(OpenIdAuthenticationProvider.class);
    private static List<GrantedAuthority> weixinAuthoritiesCache = new ArrayList<GrantedAuthority>();

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    // private MemberMapper memberMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        loadWeinxinAuthorityCache();
    }

    public void loadWeinxinAuthorityCache() {
        long startTime = System.currentTimeMillis();
        LOG.info("========更新微信用户角色的授权列表开始========");
        // 获取角色为WEIXIN（微信用户）的所有权限
        MapEntity params = new MapEntity();
        params.put("f_role_code", RoleEntity.ROLE_WEIXIN);
        List<RoleEntity> roles = roleMapper.selectEntityListPage(params);
        if (HelpUtil.isEmptyCollection(roles)) {
            throw new BadCredentialsException("角色[WEIXIN - 微信用户]未配置，请联系系统管理员！");
        }

        params.clear();
        params.put("f_role_id", roles.get(0).getF_id());
        List<MapEntity> ruleMenuList = roleMenuMapper.selectEntityListPage(params);

        weixinAuthoritiesCache.clear();
        for (MapEntity ruleMenu : ruleMenuList) {
            weixinAuthoritiesCache.add(new SimpleGrantedAuthority(ruleMenu.getString("f_menu_id")));
        }

        LOG.info("========更新微信用户角色的授权列表完成，耗时：{}ms========\n角色[{}]的授权列表为：{}", (System.currentTimeMillis() - startTime),
                RoleEntity.ROLE_WEIXIN, weixinAuthoritiesCache);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (OpenIdAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication token) throws AuthenticationException {
        OpenIdAuthenticationToken authRequest = (OpenIdAuthenticationToken) token;

        String authorizationCode = authRequest.getAuthorizationCode();
        String openid = authRequest.getOpenid();
        // 本地浏览器模拟手机端时，手动输入openid，不用到微信服务器去获取openid。授权码同openid。
        if (openid == null) {
            openid = getOpenidByAuthorizationCode(authorizationCode);
        } else {
            authorizationCode = openid;
        }

        SecurityUser securityUser = buildSecurityUser(authorizationCode, openid);
        OpenIdAuthenticationToken authResult = new OpenIdAuthenticationToken(authorizationCode, openid, securityUser,
                securityUser.getAuthorities());
        LOG.info("openid is: " + authResult.getOpenid());

        return authResult;
    }

    private String getOpenidByAuthorizationCode(String authorizationCode) {
        // TODO 这里的appid和secret需要改为用properties文件来配置
        HttpGet request = new HttpGet(
                "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9504135e7dbe2999&secret=9229e5d3954ba97b63b367b45943a5bb&code="
                        + authorizationCode + "&grant_type=authorization_code");
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
            return (String) result.get("openid");
            // authResult.setOpenid((String) result.get("openid"));
            // authResult.setAccessToken((String) result.get("access_token"));
            // authResult.setExpiresIn((Integer) result.get("expires_in"));
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

    private SecurityUser buildSecurityUser(String authorizationCode, String openid) {
        SecurityUser securityUser = new SecurityUser(openid, authorizationCode, weixinAuthoritiesCache);

        MapEntity params = new MapEntity();
        params.put("f_openid", openid);
        // List<MemberEntity> members = memberMapper.selectEntityListPage(params);
        // if (members.size() > 0) {
        // securityUser.setMember(members.get(0));
        // } else {
        // throw new BadCredentialsException("还未注册，请先关注商砼之家微信公众号！");
        // }

        return securityUser;
    }
}
