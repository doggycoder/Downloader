/*
 *
 * Cache.java
 * 
 * Created by Wuwang on 2016/9/10
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader.file;


import com.wuwang.downloader.DownloaderException;

/**
 * Description:
 */
public interface Cache {

    long length() throws DownloaderException;

    int read(byte[] buffer, int start, int length) throws DownloaderException;

    void append(byte[] data, int length) throws DownloaderException;

    void close(boolean isComplete) throws DownloaderException;

    boolean isComplete();

}
