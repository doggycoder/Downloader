package com.wuwang.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwang on 2016/9/15
 */
public class DownloadListAdapter extends RecyclerView.Adapter<DownloadHolder> {

    private Context context;
    public List<DownloadInfo> data;

    public DownloadListAdapter(Context context){
        data=new ArrayList<>();
        this.context=context;
    }

    public int indexOf(String a){
        int size=data.size();
        for (int i=0;i<size;i++){
            if (data.get(i).mAddress.equals(a)){
                return i;
            }
        }
        return -1;
    }

    public boolean contains(String a){
        return indexOf(a)!=-1;
    }

    @Override
    public DownloadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownloadHolder(LayoutInflater.from(context).inflate(R.layout.item_download,null,false));
    }

    @Override
    public void onBindViewHolder(DownloadHolder holder, int position) {
        holder.setInfo(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
