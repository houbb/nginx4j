package com.github.houbb.nginx4j.exception;

import com.github.houbb.heaven.response.respcode.RespCode;

public class Nginx4jException extends RuntimeException {

    public Nginx4jException() {
    }

    public Nginx4jException(final RespCode respCode) {
        super(respCode.getCode()+": " + respCode.getMsg());
    }

    public Nginx4jException(String message) {
        super(message);
    }

    public Nginx4jException(String message, Throwable cause) {
        super(message, cause);
    }

    public Nginx4jException(Throwable cause) {
        super(cause);
    }

    public Nginx4jException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
