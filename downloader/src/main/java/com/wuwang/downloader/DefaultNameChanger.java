/*
 *
 * DefaultNameChanger.java
 * 
 * Created by Wuwang on 2016/9/24
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader;

import com.wuwang.downloader.abs.NameChanger;

/**
 * Description:
 */
public class DefaultNameChanger implements NameChanger {

    @Override
    public String change(String t) {
        int start=t.lastIndexOf("/");
        int end=t.lastIndexOf("?");
        if(end<start||end==-1){
            end=t.length();
        }
        return t.substring(start,end);
    }

}
