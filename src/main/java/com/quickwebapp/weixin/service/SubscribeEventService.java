/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;
import com.quickwebapp.weixin.entity.UserInfo;

/**
 * @author 袁进勇
 *
 */
public interface SubscribeEventService {
    /**
     * 收到微信服务器发送过来的订阅、关注事件时调用该接口
     * 
     * @param reqMsg
     * @param userInfo
     * @return
     */
    Message onSubscribe(Message reqMsg, UserInfo userInfo);
}
