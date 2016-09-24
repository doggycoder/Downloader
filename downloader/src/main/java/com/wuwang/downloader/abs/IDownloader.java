/*
 *
 * IDownloader.java
 * 
 * Created by Wuwang on 2016/9/22
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader.abs;


import com.wuwang.frame.Cache;
import com.wuwang.frame.Seeker;
import com.wuwang.frame.Source;

/**
 * Description: 下载器接口
 */
public interface IDownloader {

    void download(Source source,Cache cache);
    void cancel();
    void setDownloadObserver(DownloadObserver observer);

}
