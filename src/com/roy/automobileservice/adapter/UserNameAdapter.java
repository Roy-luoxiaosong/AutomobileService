package com.roy.automobileservice.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.UserNameItem;
import com.roy.automobileservice.utils.Utils;


public class UserNameAdapter extends ArrayAdapter<UserNameItem> implements View.OnClickListener{

    public static List<UserNameItem> list ;
    private int itemPos;
    private String username;
    public UserNameAdapter(Context context, int resource, List<UserNameItem> objects) {
        super(context, resource, objects);
        list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserNameItem userNameItem = getItem(position);
        View view;
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.username_item, null);
            viewHolder.userName_tv = (TextView) view.findViewById(R.id.usernameText);
            //显示账号
            username = userNameItem.getUserName();
            viewHolder.userName_tv.setText(username);
            //显示头像
            viewHolder.avatar_img = (ImageView) view.findViewById(R.id.avatar_img);
            viewHolder.avatar_img.setImageResource(userNameItem.getAvatarImage());
            //添加删除按钮的click监听事件
            viewHolder.del_btn = (ImageButton) view.findViewById(R.id.del_btn);
            //保存当前按钮的位置
            viewHolder.del_btn.setTag(position);
            viewHolder.del_btn.setOnClickListener(this);

            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            //显示账号
            username = userNameItem.getUserName();
            viewHolder.userName_tv.setText(username);
          //显示头像
            viewHolder.avatar_img.setImageResource(userNameItem.getAvatarImage());
            
            //添加删除按钮的click监听事件
            viewHolder.del_btn = (ImageButton) view.findViewById(R.id.del_btn);
            viewHolder.del_btn.setOnClickListener(this);
        }
        return view;
    }

    /**
     * 删除已经保存的账号
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //删除当前item
            case R.id.del_btn:
                /**
                 * 以后添加确认对话框防止误删
                 */
            	
            	itemPos = (Integer)v.getTag();
            	
            	Utils.showTipAndDeleteUer(getContext(), R.string.tip_msg_to_delete_user, itemPos,UserNameAdapter.this);
                break;
        }
    }
    class ViewHolder{
        TextView userName_tv;
        ImageView avatar_img;
        ImageButton del_btn;
    }

}
