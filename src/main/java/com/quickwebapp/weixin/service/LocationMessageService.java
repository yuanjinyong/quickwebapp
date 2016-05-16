/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;

/**
 * @author 袁进勇
 *
 */
public interface LocationMessageService {
    /**
     * 收到微信服务器发送过来的地理位置消息时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    Message onLocationMessage(Message reqMsg);
}
