package com.quickwebapp.weixin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickwebapp.weixin.autoconfigure.WeixinProperties;
import com.quickwebapp.weixin.entity.UserInfo;
import com.quickwebapp.weixin.http.client.WeixinClient;
import com.quickwebapp.weixin.service.UserInfoService;

@Service(value = "userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private WeixinProperties properties;
    @Autowired
    private WeixinClient weixinClient;

    /*
     * (non-Javadoc)
     * 
     * @see com.jianyanxinxi.weixin.service.UserInfoService#getUserInfo(java.lang.String, java.lang.String)
     */
    @Override
    public UserInfo getUserInfo(String openId, String language) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("openid", openId);
        params.put("lang", language);
        Map<String, Object> result = weixinClient.get(properties.getUserInfoUrl(), params);

        UserInfo userInfo = new UserInfo();
        userInfo.putAll(result);

        return userInfo;
    }
}
