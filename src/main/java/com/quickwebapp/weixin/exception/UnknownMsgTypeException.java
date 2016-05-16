package com.quickwebapp.weixin.exception;

import com.quickwebapp.framework.core.exception.UnknownException;

public class UnknownMsgTypeException extends UnknownException {
    private static final long serialVersionUID = 3998562115986817500L;

    public UnknownMsgTypeException(String msgType) {
        super("消息类型", msgType);
    }
}
