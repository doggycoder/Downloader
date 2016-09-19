package com.wuwang.app;

/**
 * Created by wuwang on 2016/9/16
 */
public enum DownloadState {

    INIT("开始"),DOWNLOADING("暂停"),PAUSE("继续"),COMPLETED("重下");

    private String text;

    DownloadState(String text){
        this.text=text;
    }

    @Override
    public String toString() {
        return text;
    }
}
