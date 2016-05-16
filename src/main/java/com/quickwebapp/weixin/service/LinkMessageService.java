/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;

/**
 * @author 袁进勇
 *
 */
public interface LinkMessageService {
    /**
     * 收到微信服务器发送过来的链接消息时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    Message onLinkMessage(Message reqMsg);
}
