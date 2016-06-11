package com.roy.automobileservice.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.roy.automobileservice.cls.CarOrder;

import java.util.List;

/**
 * Created by Roy on 2016/6/10.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int mResourceId;
    public CommonAdapter(Context context,int resourceId, List<T> datas){
        this.mContext = context;
        this.mDatas = datas;
        this.mResourceId = resourceId;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHoler holer = BaseViewHoler.get(mContext,convertView,parent,mResourceId,position);
        convert(holer,getItem(position));
        return holer.getmConvertView();
    }
    public abstract void convert(BaseViewHoler holer,T t);

}
