package com.gavs.hishear.m3interface;

/**
 * User: v.martos
 * Date: 07.07.14
 */
public class M3APIException extends Exception {

    public M3APIException() {
    }

    public M3APIException(String message) {
        super(message);
    }

    public M3APIException(String message, Throwable cause) {
        super(message, cause);
    }

    public M3APIException(Throwable cause) {
        super(cause);
    }
}
