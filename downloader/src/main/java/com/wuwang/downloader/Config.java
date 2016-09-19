package com.wuwang.downloader;

import android.os.Environment;
import android.util.Log;

import com.wuwang.downloader.file.Changer;
import com.wuwang.downloader.file.IRecorder;
import com.wuwang.downloader.file.Seeker;
import com.wuwang.downloader.file.SingleLineRecorder;

import java.io.File;

/**
 * Description:
 */
public class Config {

    public final Changer<String> nameChanger;
    public final Seeker<String,File> seeker;
    public final String downloadPath;
    public final IRecorder recorder;

    private Config(Changer<String> nameChanger, Seeker<String,File> seeker, String downloadPath, IRecorder recorder){
        this.downloadPath=checkNotNull(downloadPath);
        this.nameChanger=checkNotNull(nameChanger);
        this.seeker=checkNotNull(seeker);
        this.recorder=checkNotNull(recorder);
    }

    public static class Builder{

        private Changer<String> nameChanger;
        private Seeker<String,File> seeker;
        private String downloadPath;
        private IRecorder recorder;

        public Builder setNameChanger(Changer<String> nameChanger){
            this.nameChanger=nameChanger;
            return this;
        }

        public Builder setSeeker(Seeker<String,File> seeker){
            this.seeker=seeker;
            return this;
        }

        public Builder setDownloadPath(String downloadPath){
            this.downloadPath=downloadPath;
            return this;
        }

        public Builder setRecorder(IRecorder recorder){
            this.recorder=recorder;
            return this;
        }

        public Config build(){
            return new Config(nameChanger,seeker,downloadPath,recorder);
        }

    }

    private String checkNotNull(String downloadPath){
        if(downloadPath==null){
            downloadPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/downloader";
            File file=new File(downloadPath);
            if(!file.exists()){
                boolean b=file.mkdirs();
                if(!b){
                    try {
                        throw new DownloaderException("please check your permission android.permission.WRITE_EXTERNAL_STORAGE");
                    } catch (DownloaderException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.e("wuwang","创建下载目录->"+file.getAbsolutePath());
                }
            }

            Log.e("wuwang","存在下载目录->"+file.getAbsolutePath());
        }
        return downloadPath;
    }

    private Changer<String> checkNotNull(Changer<String> nameChanger){
        if(nameChanger==null){
            nameChanger=new Changer<String>() {
                @Override
                public String change(String s) {
                    return s.substring(s.lastIndexOf("/"));
                }
            };
        }
        return nameChanger;
    }

    private Seeker<String,File> checkNotNull(Seeker<String,File> seeker){
        if(seeker==null){
            seeker=new Seeker<String, File>() {
                @Override
                public File seek(String index) {
                    String fileName=nameChanger.change(index);
                    return new File(downloadPath,fileName);
                }
            };
        }
        return seeker;
    }

    private IRecorder checkNotNull(IRecorder recorder){
        if(recorder==null){
            recorder=new SingleLineRecorder();
        }
        return recorder;
    }

}
