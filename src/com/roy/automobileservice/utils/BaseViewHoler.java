package com.roy.automobileservice.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Roy on 2016/6/10.
 */
public class BaseViewHoler {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    public BaseViewHoler(Context context, ViewGroup parent,int layoutId,int position){
        this.mPosition = position;
        this.mViews = new SparseArray<>();

        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);

        mConvertView.setTag(this);
    }
    public static BaseViewHoler get(Context context,View convertView,ViewGroup parent,int layoutId,int position){
        if(convertView==null){
            return new BaseViewHoler(context,parent,layoutId,position);
        }else{
            BaseViewHoler holer = (BaseViewHoler)convertView.getTag();
            holer.mPosition = position;
            return holer;
        }
    }

    /**
     * 通过viewid获取控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }
    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 链式编程
     * @param viewId
     * @param text
     * @return
     */
    public BaseViewHoler setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
    public BaseViewHoler setButtonText(int viewId,String text){
        Button button = getView(viewId);
        button.setText(text);
        return this;
    }
}
