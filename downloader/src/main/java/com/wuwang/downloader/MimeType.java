/*
 *
 * MimeType.java
 * 
 * Created by Wuwang on 2016/9/10
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;

/**
 * Description:
 */
public enum MimeType {

   ;

    private String type;
    MimeType(String type){
        this.type=type;
    }

    @Override
    public String toString() {
        return type;
    }
}
