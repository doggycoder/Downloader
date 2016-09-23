package com.wuwang.app;

import android.app.Application;

import com.wuwang.downloader.Config;
import com.wuwang.downloader.DownloaderManager;

/**
 * Created by wuwang on 2016/9/16
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDownloader();
    }

    private void initDownloader(){
        Config config=new Config.Builder().setDownloadPath(getExternalFilesDir("download").getAbsolutePath())
                .build();
        DownloaderManager.getInstance().setConfig(config);
    }
}
