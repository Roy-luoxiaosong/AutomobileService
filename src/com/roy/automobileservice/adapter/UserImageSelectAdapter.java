package com.roy.automobileservice.adapter;

import java.util.List;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.HeadSculpture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserImageSelectAdapter extends BaseAdapter{
	private List<HeadSculpture> mList;
	private Context mContext;
	
	public UserImageSelectAdapter(Context mContext,List<HeadSculpture> mList) {
		super();
		this.mList = mList;
		this.mContext = mContext;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}
	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View view;
		 ViewHolder viewHolder;
		 if(convertView==null){
			 view = LayoutInflater.from(mContext).inflate(R.layout.user_image_select_item, null);
			 viewHolder = new ViewHolder();
			 viewHolder.name = (TextView)view.findViewById(R.id.imag_name);
			 viewHolder.icon = (ImageView)view.findViewById(R.id.select_avatar_img);
			 view.setTag(viewHolder);
		 }else{
			 view = convertView;
			 viewHolder = (ViewHolder)view.getTag();
		 }
		 viewHolder.name.setText(mList.get(position).getName());
		 viewHolder.icon.setImageResource(mList.get(position).getImageId());
		 return view;
		 
	}
	class ViewHolder{
		ImageView icon;
		TextView name;
	}

}
