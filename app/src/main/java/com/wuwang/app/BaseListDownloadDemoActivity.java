package com.wuwang.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by wuwang on 2016/9/15
 */
public class BaseListDownloadDemoActivity extends AppCompatActivity {

    public RecyclerView mRecycler;
    public Button mAdd;
    public EditText mAddress;
    private DownloadListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);
        mRecycler= (RecyclerView) findViewById(R.id.mList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(layoutManager);
        adapter=new DownloadListAdapter(this);
        mRecycler.setAdapter(adapter);
        mAddress= (EditText) findViewById(R.id.mAddress);
        mAdd= (Button) findViewById(R.id.mAdd);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUrl=mAddress.getText().toString();
                if(-1==adapter.indexOf(mUrl)){
                    mAddress.setText("");
                    adapter.data.add(new DownloadInfo(mUrl));
                    adapter.notifyDataSetChanged();
                    mRecycler.setLayoutFrozen(false);
                }else{
                    toast("下载任务已经添加");
                }
            }
        });
    }

    public void toast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
