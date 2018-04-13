package com.yinx.hjpa.exception;

/**
 * Created by seany on 2018/3/16.
 */
public class IocInstanceNotFoundException extends IocException {
    private static final long serialVersionUID = -742775077430352894L;

    public IocInstanceNotFoundException() {
    }

    public IocInstanceNotFoundException(String message) {
        super(message);
    }

    public IocInstanceNotFoundException(Throwable cause) {
        super(cause);
    }

    public IocInstanceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
