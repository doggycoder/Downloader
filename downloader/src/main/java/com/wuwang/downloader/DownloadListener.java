/*
 *
 * DownloadListener.java
 * 
 * Created by Wuwang on 2016/9/10
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;

/**
 * Description:
 */
public interface DownloadListener {

    int START=1;
    int DOWNLOADING=2;
    int RENAME=3;
    int EXIST=4;
    int CANCEL=5;

    int ERR_LOCK=2;

    void onProgress(long sourceSize, long cacheSize, int state);
    void onError(int error);

}
