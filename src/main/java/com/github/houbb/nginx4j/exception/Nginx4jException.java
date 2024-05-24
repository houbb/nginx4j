package com.github.houbb.nginx4j.exception;

public class Nginx4jException extends RuntimeException {

    public Nginx4jException() {
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
