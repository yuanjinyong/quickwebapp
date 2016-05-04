package com.quickwebapp.framework.core.exception;

public class UnSupportedOperationException extends Exception {
    private static final long serialVersionUID = 1L;
    private String operation;

    public UnSupportedOperationException() {
        super();
    }

    public UnSupportedOperationException(String message) {
        super(message);
    }

    public UnSupportedOperationException(String message, String operation) {
        super(message);
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
