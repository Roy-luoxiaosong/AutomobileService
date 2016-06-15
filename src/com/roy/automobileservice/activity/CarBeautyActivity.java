package com.roy.automobileservice.activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.BeautyCategory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.SQLQueryListener;



/**
 * Created by Roy on 2016/5/11.
 */
public class CarBeautyActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    public static void startAction(Context context){
        Intent intent = new Intent(context,CarBeautyActivity.class);
        context.startActivity(intent);
    }
    private Button beautyRequest;
    private ListView names;
    private List<BeautyCategory> beautyCategoryList = new ArrayList<>();
    private BmobQuery<BeautyCategory> query;
    private List<String> nameList = new ArrayList<>();

    private ProgressBar progressBar;

    private ImageView carBeautyImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.car_beauty_activity_view);
        initViews();
        initDatas();
        TextView textView = (TextView)findViewById(R.id.title_back_text);
        textView.setText(getResources().getString(R.string.auto_beauty_option_text));
        //Log.d("tag","名字数长度为  ："+nameList.size());





    }
    private void initViews(){
        names = (ListView)findViewById(R.id.beauty_catogory_name);
        carBeautyImageView = (ImageView)findViewById(R.id.car_beauty_image);
        beautyRequest = (Button)findViewById(R.id.beauty_catogory_beauty_request);
        progressBar = (ProgressBar)findViewById(R.id.beauty_progressBar);
        beautyRequest.setOnClickListener(this);
    }
    private void initDatas(){
        beautyCategoryList.clear();
        String bql = "select * from BeautyCategory";
            new BmobQuery<BeautyCategory>().doSQLQuery(CarBeautyActivity.this, bql, new SQLQueryListener<BeautyCategory>() {
                @Override
                public void done(BmobQueryResult<BeautyCategory> bmobQueryResult, BmobException e) {
                    if(e==null){
                        beautyCategoryList = bmobQueryResult.getResults();
                        Iterator<BeautyCategory> it = beautyCategoryList.iterator();
                        while (it.hasNext()){
                            nameList.add(it.next().getName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(CarBeautyActivity.this,
                                android.R.layout.simple_list_item_1,nameList);
                        names.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        carBeautyImageView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        names.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                BeautyCategory beautyCategory = beautyCategoryList.get(position);
                                BeautyCatogoryContentActivity.startAction(CarBeautyActivity.this,beautyCategory.getName(),beautyCategory.getContent());
                            }
                        });
                    }else {
                        Toast.makeText(CarBeautyActivity.this,"加载数据失败，请检查网络设置",Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BeautyCategory beautyCategory = beautyCategoryList.get(position);
        BeautyCatogoryContentActivity.startAction(CarBeautyActivity.this,beautyCategory.getName(),beautyCategory.getContent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.beauty_catogory_beauty_request:
                AskForBeautyActivity.startAction(CarBeautyActivity.this);

        }
    }
}
