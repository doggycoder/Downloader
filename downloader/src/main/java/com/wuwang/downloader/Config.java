package com.wuwang.downloader;

import android.os.Environment;
import android.util.Log;

import com.wuwang.downloader.abs.NameChanger;
import com.wuwang.exception.CacheException;
import com.wuwang.frame.Seeker;

import java.io.File;

/**
 * Description: 下载管理器配置
 */
public class Config {

    public final NameChanger nameChanger;
    public final Seeker<String,File> seeker;
    public final String downloadPath;

    private Config(NameChanger nameChanger, Seeker<String,File> seeker, String downloadPath){
        this.downloadPath=checkNotNull(downloadPath);
        this.nameChanger=checkNotNull(nameChanger);
        this.seeker=checkNotNull(seeker);
    }

    public static class Builder{

        private NameChanger nameChanger;
        private Seeker<String,File> seeker;
        private String downloadPath;

        public Builder setNameChanger(NameChanger nameChanger){
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

        public Config build(){
            return new Config(nameChanger,seeker,downloadPath);
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
                        throw new CacheException("please check your permission android.permission.WRITE_EXTERNAL_STORAGE");
                    } catch (CacheException e) {
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

    private NameChanger checkNotNull(NameChanger nameChanger){
        if(nameChanger==null){
            nameChanger=new DefaultNameChanger();
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

}
