/*
 *
 * AysncDownloader.java
 * 
 * Created by Wuwang on 2016/9/22
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;

import android.os.AsyncTask;

import com.wuwang.downloader.abs.DownloadObserver;
import com.wuwang.downloader.abs.IDownloader;
import com.wuwang.exception.CacheException;
import com.wuwang.exception.SourceException;
import com.wuwang.frame.Cache;
import com.wuwang.frame.Source;

/**
 * Description:
 */
public class AsyncDownloader extends AsyncTask<Boolean,Object,Integer> implements IDownloader,DownloadObserver {

    private DownloadObserver mObserver;
    private boolean cancelFlag=false;
    private Source mSource;
    private Cache mCache;
    private long fileSize;
    private long cacheSize;

    public AsyncDownloader(){
        super();
    }

    @Override
    protected Integer doInBackground(Boolean... params) {
        downloadAct();
        return null;
    }

    private void downloadAct(){
        cancelFlag=false;
        if(checkFileExit())return;
        try {
            mSource.open(mCache.length());
            fileSize=mSource.length();
            onStart(mCache.length(),fileSize);
            byte[] buffer=new byte[4096];
            int count;
            while (!cancelFlag&&-1!=(count=mSource.read(buffer))){
                mCache.append(buffer,count);
                cacheSize=mCache.length();
                onProgress(cacheSize,fileSize);
            }
            if(!cancelFlag||cacheSize==fileSize){
                mCache.close(true);
                onFinish(mCache,COMPLETED);
            }else{
                mCache.close(false);
                onFinish(mCache,CANCLED);
            }
        } catch (SourceException | CacheException e) {
            e.printStackTrace();
            try {
                mSource.close();
                mCache.close(false);
            } catch (CacheException | SourceException e1) {
                e1.printStackTrace();
            }finally {
                onFinish(mCache,ERROR_UNKNOW);
            }
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        if(mObserver!=null){
            switch ((int)values[0]){
                case 1:
                    mObserver.onStart((long)values[1],(long)values[2]);
                    break;
                case 2:
                    mObserver.onProgress((long)values[1],(long)values[2]);
                    break;
                case 3:
                    mObserver.onFinish((Cache) values[1],(int)values[2]);
                    break;
            }
        }
    }

    @Override
    public void download(Source source, Cache cache) {
        this.mSource=source;
        this.mCache=cache;
        execute();
    }

    @Override
    public void cancel() {
        cancelFlag=true;
    }

    @Override
    public void setDownloadObserver(DownloadObserver observer) {
        this.mObserver=observer;
    }

    private boolean checkFileExit(){
        if(mCache.isComplete()){
            if(mObserver!=null){
                long length=-1;
                try {
                    length=mCache.length();
                } catch (CacheException e) {
                    e.printStackTrace();
                }
                onStart(length,length);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onStart(long cacheSize, long totalSize) {
        publishProgress(1,cacheSize,totalSize);
    }

    @Override
    public void onProgress(long cacheSize, long totalSize) {
        publishProgress(2,cacheSize,totalSize);
    }

    @Override
    public void onFinish(Cache cache,int type) {
        publishProgress(3,cache,type);
    }

}
