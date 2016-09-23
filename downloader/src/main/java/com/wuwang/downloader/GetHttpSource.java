package com.wuwang.downloader;

import com.wuwang.frame.Source;

/**
 * Created by wuwang on 2016/9/22
 */
public class GetHttpSource implements Source {

    private String mUrl;

    public GetHttpSource(String url){
        this.mUrl=url;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public String getParams(String key) {
        return null;
    }
}
