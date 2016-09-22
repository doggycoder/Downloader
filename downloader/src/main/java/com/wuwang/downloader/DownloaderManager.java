package com.wuwang.downloader;

import android.os.AsyncTask;

import com.wuwang.downloader.file.FileCache;

/**
 * Description: 文件下载管理，也是文件下载的入口。
 * 单例模式，一个应用中只有一个下载管理器。下载管理器用于管理所有的现在任务{@link ADownloader}.
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
    public void download(final String url,final DownloadListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Config config=getConfig();
                if(config.recorder.isLock(url)){
                    if(listener!=null){
                        listener.onError(DownloadListener.ERR_LOCK);
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
                singleDownloader.setDownloadListener(listener);
                singleDownloader.download(new Source() {
                    @Override
                    public String getUrl() {
                        return url;
                    }

                    @Override
                    public String getParams(String key) {
                        return null;
                    }
                },new FileCache(getConfig().seeker.seek(url)));
                config.recorder.unlock(url);
            }
        }).start();
    }

    public void cancel(final String url){
        Config config=getConfig();
        if(config.recorder.isLock(url)&&singleDownloader!=null){
            singleDownloader.cancel();
        }
    }

}
