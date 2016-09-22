/*
 *
 * IDownloader.java
 * 
 * Created by Wuwang on 2016/9/22
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;


import com.wuwang.downloader.file.Cache;

/**
 * Description:
 */
public interface IDownloader {

    void download(Source source, Cache cache);
    void cancel();
    void setDownloadObserver(DownloadObserver observer);

}
