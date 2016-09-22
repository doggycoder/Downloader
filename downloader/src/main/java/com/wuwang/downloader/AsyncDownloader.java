/*
 *
 * AysncDownloader.java
 * 
 * Created by Wuwang on 2016/9/22
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.wuwang.downloader.file.Cache;
import com.wuwang.downloader.file.FileCache;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_MOVED_PERM;
import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;
import static java.net.HttpURLConnection.HTTP_SEE_OTHER;

/**
 * Description:
 */
public class AsyncDownloader extends AsyncTask<ITarget,Long,Integer> implements IDownloader,DownloadListener {

    private DownloadListener mDownloadListener;
    private boolean cancelFlag=false;
    private Source mSource;
    private Cache mCache;
    private long fileSize;
    private long cacheSize;

    public AsyncDownloader(){
        super();
    }

    @Override
    protected Integer doInBackground(ITarget... params) {
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
                onProgress(fileSize,mCache.length(),DownloadListener.START);
                byte[] buffer=new byte[4096];
                int count;
                while (!cancelFlag&&-1!=(count=inStream.read(buffer))){
                    mCache.append(buffer,count);
                    cacheSize=mCache.length();
                    onProgress(fileSize,cacheSize,DownloadListener.DOWNLOADING);
                }
                if(!cancelFlag||cacheSize==fileSize){
                    mCache.close(true);
                    onProgress(fileSize,cacheSize,DownloadListener.RENAME);
                }else{
                    mCache.close(false);
                    onProgress(fileSize,cacheSize,DownloadListener.CANCEL);
                }
                Log.e("wuwang","下载完成->"+ Environment.getExternalStorageDirectory().getPath()+"/test.mp4");
            }
        } catch (IOException | DownloaderException e) {
            e.printStackTrace();
            try {
                mCache.close(false);
                onError(0);
            } catch (DownloaderException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        if(mDownloadListener!=null){
            if(values.length==2){
                mDownloadListener.onError((int)(long)values[1]);
            }else{
                mDownloadListener.onProgress(values[0],values[1],(int)(long)values[2]);
            }
        }
    }

    @Override
    public void download(Source source, Cache cache) {
        this.mSource=source;
        this.mCache=cache;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void setDownloadListener(DownloadListener listener) {
        this.mDownloadListener=listener;
    }

    private boolean checkFileExit(){
        if(mCache.isComplete()){
            if(mDownloadListener!=null){
                long length=-1;
                try {
                    length=mCache.length();
                } catch (DownloaderException e) {
                    e.printStackTrace();
                }
                mDownloadListener.onProgress(length,length,DownloadListener.START);
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
    public void onProgress(long sourceSize, long cacheSize, int state) {
        publishProgress(sourceSize,cacheSize,(long)state);
    }

    @Override
    public void onError(int error) {
        publishProgress(0L,(long)error);
    }
}
