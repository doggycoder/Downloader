package com.wuwang.downloader;

/**
 * Description:
 */
public class DownloaderException extends Exception {

    public DownloaderException(String detailMessage) {
        super(detailMessage);
    }

    public DownloaderException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
