package com.quickwebapp.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.quickwebapp.weixin.entity.Message;
import com.quickwebapp.weixin.service.TextMessageService;

@Service(value = "textMessageService")
public class TextMessageServiceImpl implements TextMessageService {

    @Override
    public Message onTextMessage(Message reqMsg) {
        Message respMsg = new Message();
        respMsg.setToUserName(reqMsg.getFromUserName());
        respMsg.setFromUserName(reqMsg.getToUserName());
        respMsg.setCreateTime(System.currentTimeMillis());
        respMsg.setMsgType(Message.MSG_TYPE_TEXT);
        respMsg.setContent("消息接收成功，您发送的内容为：" + reqMsg.getContent());
        return respMsg;
    }
}
