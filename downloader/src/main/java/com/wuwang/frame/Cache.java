package com.wuwang.frame;


import com.wuwang.exception.CacheException;

/**
 * Description:
 */
public interface Cache {

    void open() throws CacheException;

    long length() throws CacheException;

    int read(byte[] buffer, int start, int length) throws CacheException;

    void append(byte[] data, int length) throws CacheException;

    void close(boolean isComplete) throws CacheException;

    boolean delete();

    boolean isComplete();

}
