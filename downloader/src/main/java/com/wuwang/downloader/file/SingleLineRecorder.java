/*
 *
 * SingleLineRecorder.java
 * 
 * Created by Wuwang on 2016/9/10
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader.file;

/**
 * Description: 单线程的下载记录器
 */
public class SingleLineRecorder implements IRecorder {

    private String nowUrl;

    public SingleLineRecorder(){

    }

    @Override
    public synchronized void lock(String url) {
        this.nowUrl=url;
    }

    @Override
    public synchronized void unlock(String url) {
        this.nowUrl=null;
    }

    @Override
    public synchronized boolean isLock(String url) {
        return this.nowUrl != null && this.nowUrl.endsWith(url);
    }

}
