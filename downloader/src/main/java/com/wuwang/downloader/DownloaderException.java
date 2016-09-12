/*
 *
 * DownloaderException.java
 * 
 * Created by Wuwang on 2016/9/10
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;

/**
 * Description:
 */
public class DownloaderException extends Exception {

    public DownloaderException(String detailMessage) {
        super(detailMessage);
    }

    public DownloaderException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
