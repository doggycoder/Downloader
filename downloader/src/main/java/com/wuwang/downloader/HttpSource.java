package com.wuwang.downloader;

import android.util.Log;

import com.wuwang.downloader.abs.RemoteSource;
import com.wuwang.exception.SourceException;
import com.wuwang.utils.ContentType;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_MOVED_PERM;
import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;
import static java.net.HttpURLConnection.HTTP_SEE_OTHER;

/**
 * Created by wuwang on 2016/9/22
 */
public class HttpSource implements RemoteSource{

    private String mUrl;
    private long sourceSize=-1;
    private HttpURLConnection conn;
    private InputStream iSream;
    private String type;

    public HttpSource(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public void open(long offset) throws SourceException{
        conn=openConnection(offset);
        if(conn!=null){
            try {
                iSream=conn.getInputStream();
            } catch (IOException e) {
                throw new SourceException("Source open failed.",e.getCause());
            }
        }
    }

    @Override
    public long length() {
        return sourceSize;
    }

    @Override
    public int read(byte[] buffer) throws SourceException{
        if(iSream!=null){
            try {
                return iSream.read(buffer);
            } catch (IOException e) {
                throw new SourceException("Source read failed.",e.getCause());
            }
        }
        return -1;
    }

    @Override
    public void close() throws SourceException {
        if(iSream!=null){
            try {
                iSream.close();
            } catch (IOException e) {
                throw new SourceException("Source close failed.",e.getCause());
            }
        }
    }

    @Override
    public String name() throws SourceException {
        if(type==null){
            throw new SourceException("Source get name error");
        }
        int start=mUrl.lastIndexOf("/");
        int end=mUrl.lastIndexOf("?");
        if(end<start||end==-1){
            end=mUrl.length();
        }
        String name=mUrl.substring(start,end);
        if(name.endsWith("\\..*$")){
            return name;
        }else{
            return name+ ContentType.getMime(type);
        }
    }

    private HttpURLConnection openConnection(long offset){
        try {
            URL httpUrl=new URL(mUrl);
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
            type=conn.getContentType();
            sourceSize=offset+conn.getContentLength();
            if(redirected){
                //重定向
            }
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
