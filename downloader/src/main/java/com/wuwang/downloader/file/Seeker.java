package com.wuwang.downloader.file;

/**
 * Description:
 */
public interface Seeker<I,O> {
    O seek(I index);
}
