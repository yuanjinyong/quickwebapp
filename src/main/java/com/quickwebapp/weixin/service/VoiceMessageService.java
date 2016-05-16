/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;

/**
 * @author 袁进勇
 *
 */
public interface VoiceMessageService {
    /**
     * 收到微信服务器发送过来的语音消息时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    Message onVoiceMessage(Message reqMsg);
}
