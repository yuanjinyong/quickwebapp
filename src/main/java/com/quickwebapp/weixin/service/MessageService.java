/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;

/**
 * @author 袁进勇
 *
 */
public interface MessageService {
    /**
     * 收到微信服务器发送过来的消息时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    Message oMessage(Message reqMsg);
}
