/*
 *
 * Seeker.java
 * 
 * Created by Wuwang on 2016/9/10
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader.file;

/**
 * Description:
 */
public interface Seeker<I,O> {
    O seek(I index);
}
