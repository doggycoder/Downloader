package com.wuwang.downloader.abs;

import com.wuwang.frame.Cache;

/**
 * Created by wuwang on 2016/9/22
 */
public interface DownloadObserver {

    int COMPLETED=0;    //下载完成
    int CANCLED=1;      //下载被取消
    int ERROR_UNKNOW=0x10;  //未知错误
    int ERROR_LOCK=0x11;    //缓存被锁定

    /**
     * 下载开始
     * @param cacheSize 已存在的缓冲大小
     * @param totalSize 目标文件的总大小
     */
    void onStart(long cacheSize,long totalSize);

    /**
     * 下载进度更新
     * @param cacheSize 缓存大小
     * @param totalSize 目标文件总大小
     */
    void onProgress(long cacheSize,long totalSize);

    /**
     * 下载结束
     * @param cache 缓存文件
     * @param type 结束原因代码
     */
    void onFinish(Cache cache,int type);

}
