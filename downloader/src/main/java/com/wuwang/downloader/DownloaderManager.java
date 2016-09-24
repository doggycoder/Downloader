package com.wuwang.downloader;

import com.wuwang.downloader.abs.DownloadObserver;
import com.wuwang.downloader.abs.IDownloader;
import com.wuwang.frame.Cache;

import java.util.HashMap;

/**
 * Description: 文件下载管理，也是文件下载的入口。
 * 单例模式，一个应用中只有一个下载管理器。
 */
public class DownloaderManager {

    private static DownloaderManager manager;
    private Config config;
    private HashMap<String,IDownloader> downloadClients;

    private DownloaderManager(){
        downloadClients=new HashMap<>();
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
        if(downloadClients.containsKey(url)){
            if(listener!=null){
                listener.onFinish(null,DownloadObserver.ERROR_LOCK);
            }
            return;
        }
        final IDownloader downloader=new AsyncDownloader();
        downloader.setDownloadObserver(new DownloadObserver() {
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
                if(listener!=null){
                    listener.onFinish(cache,type);
                }
                if(downloadClients.containsKey(url)){
                    downloadClients.remove(url);
                }
            }
        });
        downloadClients.put(url,downloader);
//        downloader.download(new HttpSource(url),);
    }

    public void cancel(final String url){
        if(downloadClients.containsKey(url)){
            downloadClients.remove(url).cancel();
        }
    }



}
