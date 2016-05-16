/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;

/**
 * @author 袁进勇
 *
 */
public interface ShortVideoMessageService {
    /**
     * 收到微信服务器发送过来的小视频消息时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    Message onShortVideoMessage(Message reqMsg);
}
