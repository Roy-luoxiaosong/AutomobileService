package com.roy.automobileservice.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.InfoItem;

public class InfoItemAdapter extends ArrayAdapter<InfoItem>{
	private int resourceId;
	
	public InfoItemAdapter(Context context, int resource, List<InfoItem> objects) {
		super(context, resource, objects);
		this.resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 InfoItem info = getItem(position);
		 View view;
		 ViewHolder viewHolder;
		 if(convertView==null){
			 view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			 viewHolder = new ViewHolder();
			 viewHolder.title = (TextView)view.findViewById(R.id.info_item_title);
			 viewHolder.content = (TextView)view.findViewById(R.id.info_item_content);
			 view.setTag(viewHolder);
		 }else{
			 view = convertView;
			 viewHolder = (ViewHolder)view.getTag();
		 }
		 viewHolder.title.setText(info.getTitle());
		 viewHolder.content.setText(info.getContent());
		 return view;
		 
	}
	class ViewHolder{
		TextView title;
		TextView content;
	}

	
}
