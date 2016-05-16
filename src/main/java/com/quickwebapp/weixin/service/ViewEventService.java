/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;

/**
 * @author 袁进勇
 *
 */
public interface ViewEventService {
    /**
     * 收到微信服务器发送过来的点击菜单跳转链接时的事件时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    Message onClick(Message reqMsg);
}
