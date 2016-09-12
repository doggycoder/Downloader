/*
 *
 * ADownloader.java
 * 
 * Created by Wuwang on 2016/9/10
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;


import com.wuwang.downloader.file.Cache;

/**
 * Description:
 */
public abstract class ADownloader{

    public String mDownloadUrl;
    public DownloadListener mDownloadListener;
    public Cache mCache;

    public ADownloader(String downloadUrl, Cache cache){
        this.mDownloadUrl=downloadUrl;
        this.mCache=cache;
    }

    public ADownloader setDownloadListener(DownloadListener listener){
        this.mDownloadListener=listener;
        return this;
    }

    public abstract void download();

    public abstract void cancel();


}
