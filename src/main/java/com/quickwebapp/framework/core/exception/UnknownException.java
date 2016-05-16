package com.quickwebapp.framework.core.exception;

/**
 * 未知XXX异常
 * 
 * @author 袁进勇
 *
 */
public abstract class UnknownException extends BusinessException {
    private static final long serialVersionUID = 8189088140335420624L;
    private String name;
    private Object value;

    public UnknownException(String name, Object value) {
        super("未知的%s[%s]。", name, value);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
