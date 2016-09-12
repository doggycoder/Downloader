/*
 *
 * DefaultDownloader.java
 * 
 * Created by Wuwang on 2016/9/10
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;

import android.os.Environment;
import android.util.Log;

import com.wuwang.downloader.file.Cache;

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
public class DefaultDownloader extends ADownloader{

    private InputStream inStream;
    private long fileSize;
    private long cacheSize;
    private boolean cancelFlag=false;

    public DefaultDownloader(String downloadUrl, Cache cache) {
        super(downloadUrl, cache);
    }

    private HttpURLConnection openConnection(long offset){
        try {
            URL httpUrl=new URL(mDownloadUrl);
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

    @Override
    public void download() {
        cancelFlag=false;
        if(checkFileExit())return;
        try {
            HttpURLConnection conn=openConnection(mCache.length());
            if(conn!=null){
                inStream=conn.getInputStream();
                if(mDownloadListener!=null){
                    mDownloadListener.onProgress(fileSize,mCache.length(),DownloadListener.START);
                }
                byte[] buffer=new byte[4096];
                int count;
                while (!cancelFlag&&-1!=(count=inStream.read(buffer))){
                    mCache.append(buffer,count);
                    cacheSize=mCache.length();
                    if(mDownloadListener!=null){
                        mDownloadListener.onProgress(fileSize,cacheSize,DownloadListener.DOWNLOADING);
                    }
                }
                if(!cancelFlag||cacheSize==fileSize){
                    mCache.close(true);
                    if(mDownloadListener!=null){
                        mDownloadListener.onProgress(fileSize,cacheSize,DownloadListener.RENAME);
                    }
                }else{
                    mCache.close(false);
                    mDownloadListener.onProgress(fileSize,cacheSize,DownloadListener.CANCEL);
                }
                Log.e("wuwang","下载完成->"+ Environment.getExternalStorageDirectory().getPath()+"/test.mp4");
            }
        } catch (IOException | DownloaderException e) {
            e.printStackTrace();
            try {
                if(mDownloadListener!=null){
                    mDownloadListener.onError(0);
                }
                mCache.close(false);
            } catch (DownloaderException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void cancel() {
        cancelFlag=true;
    }
}
