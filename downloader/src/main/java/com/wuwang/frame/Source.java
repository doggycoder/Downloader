/*
 *
 * Source.java
 * 
 * Created by Wuwang on 2016/9/22
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.frame;

import com.wuwang.exception.SourceException;

/**
 * Description: 资源
 */
public interface Source {

    void open(long offset) throws SourceException;

    long length();

    int read(byte[] buffer) throws SourceException;

    void close() throws SourceException;

}
