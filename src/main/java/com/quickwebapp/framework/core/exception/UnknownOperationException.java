package com.quickwebapp.framework.core.exception;

public class UnknownOperationException extends UnknownException {
    private static final long serialVersionUID = 7887532884263349344L;

    public UnknownOperationException(String operation) {
        super("操作类型", operation);
    }
}
