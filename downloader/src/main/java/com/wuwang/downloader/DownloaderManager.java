package com.wuwang.downloader;

import android.util.Log;

import com.wuwang.downloader.abs.DownloadObserver;
import com.wuwang.downloader.abs.IDownloader;
import com.wuwang.frame.Cache;

/**
 * Description: 文件下载管理，也是文件下载的入口。
 * 单例模式，一个应用中只有一个下载管理器。
 */
public class DownloaderManager {

    private static DownloaderManager manager;
    private Config config;
    private IDownloader singleDownloader;

    private DownloaderManager(){

    }

    public static DownloaderManager getInstance(){
        if(null == manager){
            synchronized (DownloaderManager.class){
                if(null==manager){
                    manager=new DownloaderManager();
                }
            }
        }
        return manager;
    }

    public void setConfig(Config config){
        if(config!=null){
            this.config=config;
        }
    }

    public Config getConfig(){
        if(config==null){
            this.config=new Config.Builder().build();
        }
        return this.config;
    }

    //单线程单任务下载，开始下一个会取消上一个任务
    public void download(final String url,final DownloadObserver listener){
        final Config config=getConfig();
        if(config.recorder.isLock(url)){
            if(listener!=null){
                listener.onFinish(null,DownloadObserver.ERROR_LOCK);
            }
            return;
        }
        if(singleDownloader!=null){
            singleDownloader.cancel();
        }
        config.recorder.lock(url);
        if(singleDownloader!=null){
            singleDownloader.cancel();
        }
        singleDownloader=new AsyncDownloader();
        singleDownloader.setDownloadObserver(new DownloadObserver() {
            @Override
            public void onStart(long cacheSize, long totalSize) {
                if(listener!=null){
                    listener.onStart(cacheSize, totalSize);
                }
            }

            @Override
            public void onProgress(long cacheSize, long totalSize) {
                if(listener!=null){
                    listener.onProgress(cacheSize,totalSize);
                }
            }

            @Override
            public void onFinish(Cache cache, int type) {
                config.recorder.unlock(url);
                if(listener!=null){
                    listener.onFinish(cache,type);
                }
            }
        });
        singleDownloader.download(new GetHttpSource(url),new FileCache(getConfig().seeker.seek(url)));
    }

    public void cancel(final String url){
        Config config=getConfig();
        if(config.recorder.isLock(url)&&singleDownloader!=null){
            Log.e("wuwang","取消");
            singleDownloader.cancel();
        }
    }



}
