/*
 *
 * Source.java
 * 
 * Created by Wuwang on 2016/9/22
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;

/**
 * Description:
 */
public interface Source {
    String getUrl();
    String getParams(String key);
}
