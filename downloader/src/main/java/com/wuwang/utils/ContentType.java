/*
 *
 * ContentType.java
 * 
 * Created by Wuwang on 2016/9/24
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.utils;


import java.util.HashMap;

/**
 * Description:
 */
public class  ContentType {

    private static HashMap<String,String> map=new HashMap<>();
    static {
        map.put("application/vnd.android.package-archive",".apk");
        map.put("application/x-bmp",".bmp");
        map.put("image/x-icon",".ico");
        map.put("application/x-ico",".ico");
        map.put("application/vnd.iphone",".ipa");
        map.put("image/jpeg",".jpeg");
        map.put("application/x-jpg",".jpg");
        map.put("audio/mp1",".mp1");
        map.put("audio/mp2",".mp2");
        map.put("audio/mp3",".mp3");
        map.put("video/mpeg4",".mp4");
        map.put("video/mpg",".mpg");
        map.put("video/x-mpeg",".mpe");
        map.put("application/pdf",".pdf");
        map.put("image/png",".png");
        map.put("application/x-png",".png");
        map.put("application/x-ppt",".ppt");
        map.put("application/octet-stream",".rar");
        map.put("application/vnd.rn-realmedia",".rm");
        map.put("application/vnd.rn-realmedia-vbr",".rmvb");
        map.put("application/msword",".rtf");
        map.put("application/x-tif",".tif");
        map.put("image/tiff",".tiff");
        map.put("application/x-xls",".xls");
        map.put("application/vnd.ms-excel",".xls");
        map.put("text/xml",".xml");
        map.put("application/x-silverlight-app",".xpa");
        map.put("application/x-zip-compressed",".zip");
        map.put("application/zip",".zip");
    }

    private ContentType(){
    }

    public static String getMime(String contentType,String defaultMime){
        return map.containsKey(contentType)?map.get(contentType):defaultMime;
    }

    public static String getMime(String contentType){
        return getMime(contentType,contentType.replaceAll("^.*/(.*-)*",""));
    }

}
