/*
 *
 * AysncDownloader.java
 * 
 * Created by Wuwang on 2016/9/22
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;

import android.os.AsyncTask;
import android.util.Log;

import com.wuwang.downloader.abs.DownloadObserver;
import com.wuwang.downloader.abs.IDownloader;
import com.wuwang.exception.CacheException;
import com.wuwang.frame.Cache;
import com.wuwang.frame.Source;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_MOVED_PERM;
import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;
import static java.net.HttpURLConnection.HTTP_SEE_OTHER;

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
            HttpURLConnection conn=openConnection(mCache.length());
            if(conn!=null){
                InputStream inStream = conn.getInputStream();
                onStart(mCache.length(),fileSize);
                byte[] buffer=new byte[4096];
                int count;
                while (!cancelFlag&&-1!=(count=inStream.read(buffer))){
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
            }
        } catch (IOException | CacheException e) {
            e.printStackTrace();
            try {
                mCache.close(false);
                onFinish(mCache,ERROR_UNKNOW);
            } catch (CacheException e1) {
                e1.printStackTrace();
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

    private HttpURLConnection openConnection(long offset){
        try {
            URL httpUrl=new URL(mSource.getUrl());
            HttpURLConnection conn= (HttpURLConnection) httpUrl.openConnection();
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setRequestProperty("Accept","*/*");
            if(offset>0){
                conn.setRequestProperty("Range","bytes="+offset+"-");
            }
            conn.connect();
            int code = conn.getResponseCode();
            boolean redirected = code == HTTP_MOVED_PERM || code == HTTP_MOVED_TEMP || code == HTTP_SEE_OTHER;
            Log.e("wuwang","http->Response Code:"+code);
            Log.e("wuwang","http->Content-Length:"+conn.getContentLength());
            fileSize=offset+conn.getContentLength();
            if(redirected){
                //重定向
            }
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
