/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;

/**
 * @author 袁进勇
 *
 */
public interface UnsubscribeEventService {
    /**
     * 收到微信服务器发送过来的取消关注事件时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    Message onUnsubscribe(Message reqMsg);
}
