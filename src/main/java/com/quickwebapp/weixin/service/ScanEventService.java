/**
 * 
 */
package com.quickwebapp.weixin.service;

import com.quickwebapp.weixin.entity.Message;

/**
 * @author 袁进勇
 *
 */
public interface ScanEventService {
    /**
     * 收到微信服务器发送过来的扫描带参数二维码事件时调用该接口
     * 
     * @param reqMsg
     * @return
     */
    Message onScan(Message reqMsg);
}
