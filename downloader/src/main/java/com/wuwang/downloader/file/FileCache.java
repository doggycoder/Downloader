package com.wuwang.downloader.file;

import com.wuwang.downloader.DownloaderException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Description:
 */
public class FileCache implements Cache {

    private File file;
    private RandomAccessFile dataFile;

    private static final String MIME=".cache";

    public FileCache(File file){
        try {
            File parent=file.getParentFile();
            if(!parent.exists()){
                parent.mkdirs();
            }
            this.file=file.exists()?file:new File(parent,file.getName()+MIME);
            this.dataFile=new RandomAccessFile(this.file,this.file.getName().endsWith(MIME)?"rw":"r");
        } catch (FileNotFoundException e) {
            try {
                throw new DownloaderException("file not found error",e);
            } catch (DownloaderException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public synchronized long length() throws DownloaderException {
        if(file.exists()){
            try {
                return dataFile.length();
            } catch (IOException e) {
                throw new DownloaderException("",e);
            }
        }
        return 0;
    }

    @Override
    public synchronized int read(byte[] buffer, int start, int length) throws DownloaderException {
        try {
            dataFile.seek(start);
            return dataFile.read(buffer,0,length);
        } catch (IOException e) {
            throw new DownloaderException("file read error",e);
        }
    }

    @Override
    public synchronized void append(byte[] data, int length) throws DownloaderException {
        try {
            dataFile.seek(length());
            dataFile.write(data,0,length);
        } catch (IOException e) {
            throw new DownloaderException("append error",e);
        }
    }

    @Override
    public synchronized void close(boolean isComplete) throws DownloaderException {
        if(isComplete){
            String completeFileName=file.getName().substring(0,file.getName().length()-MIME.length());
            File completeFile=new File(file.getParentFile(),completeFileName);
            boolean b=file.renameTo(completeFile);
            if(b){
                file.delete();  //下载完成删除临时文件
                file=completeFile;
            }else{
                throw new DownloaderException("file rename error");
            }
        }
        try {
            dataFile.close();
        } catch (IOException e) {
            throw new DownloaderException("file close error",e);
        }
    }

    @Override
    public boolean isComplete() {
        return file.exists()&&!file.getName().endsWith(MIME);
    }

}
