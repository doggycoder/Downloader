package com.wuwang.app;

/**
 * Created by wuwang on 2016/9/16
 */
public class DownloadInfo {

    public String mAddress;
    public float mProgress;

    public DownloadState state=DownloadState.INIT;



    public DownloadInfo(String mAddress){
        this.mAddress=mAddress;
    }

}
