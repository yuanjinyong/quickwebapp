/**
 * 
 */
package com.quickwebapp.weixin.service;

import java.util.Map;

/**
 * @author 袁进勇
 *
 */
public interface SendMessageService {
    /**
     * 主动推送消息时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    public void sendMessage(Map<String, Object> params);
}
