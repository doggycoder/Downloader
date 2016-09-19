package com.wuwang.downloader.file;


import com.wuwang.downloader.DownloaderException;

/**
 * Description:
 */
public interface Cache {

    long length() throws DownloaderException;

    int read(byte[] buffer, int start, int length) throws DownloaderException;

    void append(byte[] data, int length) throws DownloaderException;

    void close(boolean isComplete) throws DownloaderException;

    boolean isComplete();

}
