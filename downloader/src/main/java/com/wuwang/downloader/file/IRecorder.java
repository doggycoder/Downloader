/*
 *
 * IRecorder.java
 * 
 * Created by Wuwang on 2016/9/10
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader.file;

/**
 * Description: 下载记录器
 */
public interface IRecorder {

    void lock(String url);  //锁定url相对的文件，禁止操作
    void unlock(String url);    //解锁url相对的文件，允许操作
    boolean isLock(String url); //判断url相对的文件是否被锁定

}
