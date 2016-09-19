package com.wuwang.downloader.file;

/**
 * Description: 下载记录器
 */
public interface IRecorder {

    void lock(String url);  //锁定url相对的文件，禁止操作
    void unlock(String url);    //解锁url相对的文件，允许操作
    boolean isLock(String url); //判断url相对的文件是否被锁定

}
