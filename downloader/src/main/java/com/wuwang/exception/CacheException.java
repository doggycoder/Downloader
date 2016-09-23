package com.wuwang.exception;

/**
 * Description:
 */
public class CacheException extends Exception {

    public CacheException(String detailMessage) {
        super(detailMessage);
    }

    public CacheException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
