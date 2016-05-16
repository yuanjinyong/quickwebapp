package com.quickwebapp.weixin.exception;

import com.quickwebapp.framework.core.exception.BusinessException;

public class ServiceNotFoundException extends BusinessException {
    private static final long serialVersionUID = -6873482681646701299L;

    public ServiceNotFoundException(String msg, Object... args) {
        super(msg, args);
    }
}
