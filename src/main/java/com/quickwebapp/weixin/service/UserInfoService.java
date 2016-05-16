/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.UserInfo;

/**
 * @author 袁进勇
 *
 */
public interface UserInfoService {
    /**
     * 获取微信用户信息
     * 
     * @param openId
     *            普通用户的标识，对当前公众号唯一
     * @param language
     *            返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     * @return
     */
    UserInfo getUserInfo(String openId, String language);
}
