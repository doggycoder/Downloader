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
