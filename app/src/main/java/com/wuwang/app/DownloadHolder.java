package com.wuwang.app;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wuwang.downloader.abs.DownloadObserver;
import com.wuwang.downloader.DownloaderManager;
import com.wuwang.frame.Cache;

/**
 * Created by wuwang on 2016/9/16
 */
public class DownloadHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DownloadObserver {

    public TextView mAddress;
    public Button mDownload;
    public ProgressBar mProgress;
    private DownloadInfo info;

    public DownloadHolder(View itemView) {
        super(itemView);
        mAddress= (TextView) itemView.findViewById(R.id.mAddress);
        mDownload= (Button) itemView.findViewById(R.id.mDownload);
        mProgress= (ProgressBar) itemView.findViewById(R.id.mProgress);
        mDownload.setOnClickListener(this);
    }

    public void setInfo(DownloadInfo info){
        this.info=info;
        mAddress.setText(info.mAddress);
        mDownload.setText(info.state.toString());
    }

    @Override
    public void onClick(View v) {
        switch (info.state){
            case INIT:
            case PAUSE:
                info.state=DownloadState.DOWNLOADING;
                DownloaderManager.getInstance().download(mAddress.getText().toString(),DownloadHolder.this);
                break;
            case DOWNLOADING:
                DownloaderManager.getInstance().cancel(mAddress.getText().toString());
                info.state=DownloadState.PAUSE;
                break;
            case COMPLETED:
                break;
        }
        mDownload.setText(info.state.toString());
    }

    @Override
    public void onStart(long cacheSize, long totalSize) {
        if(cacheSize==totalSize){

        }
    }

    @Override
    public void onProgress(long cacheSize, long totalSize) {
        mProgress.setProgress((int)(cacheSize/(double)totalSize*10000));
    }

    @Override
    public void onFinish(Cache cache,int type) {

    }

}