/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;

/**
 * @author 袁进勇
 *
 */
public interface LocationEventService {
    /**
     * 收到微信服务器发送过来的上报地理位置事件时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    Message onLocation(Message reqMsg);
}
