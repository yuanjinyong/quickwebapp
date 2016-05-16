/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;

/**
 * @author 袁进勇
 *
 */
public interface TextMessageService {
    /**
     * 收到微信服务器发送过来的文本消息时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    Message onTextMessage(Message reqMsg);
}
