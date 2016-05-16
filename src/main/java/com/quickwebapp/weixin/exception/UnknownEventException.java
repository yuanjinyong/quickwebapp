package com.quickwebapp.weixin.exception;

import com.quickwebapp.framework.core.exception.UnknownException;

public class UnknownEventException extends UnknownException {
    private static final long serialVersionUID = 8224413688822264581L;

    public UnknownEventException(String event) {
        super("事件类型", event);
    }
}
