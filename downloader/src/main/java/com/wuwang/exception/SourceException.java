/*
 *
 * SourceException.java
 * 
 * Created by Wuwang on 2016/9/24
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.exception;

/**
 * Description:
 */
public class SourceException extends Exception {

    public SourceException(String detailMessage) {
        super(detailMessage);
    }

    public SourceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
