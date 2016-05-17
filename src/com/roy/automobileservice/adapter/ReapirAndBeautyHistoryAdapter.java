package com.roy.automobileservice.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.roy.automobileservice.R;

public class ReapirAndBeautyHistoryAdapter extends BaseExpandableListAdapter{
	private List<String> mParent = null;
    private Map<String, List<String>> mMap = null;
    private int ParentResourceId;
    private int childResourceId;
    private Context context;
	public ReapirAndBeautyHistoryAdapter(Context context,List<String> parent,Map<String, List<String>> map,
			int ParentResource,int childResource) {
		super();
		this.mParent = parent;
		this.ParentResourceId = ParentResource;
		this.childResourceId = childResource;
		this.mMap = map;
		this.context = context;
	}
	       
	        //
	        @Override
	        public Object getChild(int groupPosition, int childPosition) {
	            String key = mParent.get(groupPosition);
	            return (mMap.get(key).get(childPosition));
	        }
	 
	        //
	        @Override
	        public long getChildId(int groupPosition, int childPosition) {
	            return childPosition;
	        }
	 
	        //
	        @Override
	        public View getChildView(int groupPosition, int childPosition,
	                boolean isLastChild, View convertView, ViewGroup parent) {
	        	//String key = (String)parent.getTag(groupPosition);
	        	String key = mParent.get(groupPosition);
	            String info = mMap.get(key).get(childPosition);
	            if (convertView == null) {
	                LayoutInflater inflater = (LayoutInflater) context
	                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                convertView = inflater.inflate(childResourceId, null);
	                
	            }
	            TextView content = (TextView) convertView
	                    .findViewById(R.id.histroy_content);
	            content.setText(info);
	            
	            
	            return convertView;
	        }
	 
	        //
	        @Override
	        public int getChildrenCount(int groupPosition) {
	            String key = mParent.get(groupPosition);
	            int size=mMap.get(key).size();
	            return size;
	        }
	      //
	        @Override
	        public Object getGroup(int groupPosition) {
	            return mParent.get(groupPosition);
	        }
	 
	        @Override
	        public int getGroupCount() {
	            return mParent.size();
	        }
	 
	        @Override
	        public long getGroupId(int groupPosition) {
	            return groupPosition;
	        }
	       //
	        @Override
	        public View getGroupView(int groupPosition, boolean isExpanded,
	                View convertView, ViewGroup parent) {
	            if (convertView == null) {
	                LayoutInflater inflater = (LayoutInflater)context
	                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                convertView = inflater.inflate(ParentResourceId, null);
	            }
	            TextView title = (TextView)convertView.findViewById(R.id.history_title);
	            title.setText(mParent.get(groupPosition));
	            if(groupPosition == 0){
	            	convertView.findViewById(R.id.haha).setVisibility(View.GONE);
	            }else{
	            	convertView.findViewById(R.id.haha).setVisibility(View.VISIBLE);
	            }
	            return convertView;
	        }
	 
	        @Override
	        public boolean hasStableIds() {
	            return true;
	        }
	 
	        @Override
	        public boolean isChildSelectable(int groupPosition, int childPosition) {
	            return true;
	        }

	 
}
