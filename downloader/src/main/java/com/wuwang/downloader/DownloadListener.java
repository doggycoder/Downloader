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
