package com.roy.automobileservice.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.InfoItem;
import com.roy.automobileservice.cls.Staff;
import com.roy.automobileservice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roy on 2016/6/10.
 */
public class StaffInfoAdapter extends ArrayAdapter<Staff> {
    private int mResourceId;
    private List<Staff> mData = new ArrayList<>();
    public StaffInfoAdapter(Context context, int resource, List<Staff> data) {
        super(context, resource, data);
        mResourceId = resource;
        mData = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Staff staff = mData.get(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)view.findViewById(R.id.staff_name);
            viewHolder.tel = (TextView)view.findViewById(R.id.staff_tel);
            viewHolder.emial = (TextView)view.findViewById(R.id.staff_email);
            viewHolder.deleteBtn = (ImageButton)view.findViewById(R.id.delete_staff_btn);
            viewHolder.depart = (TextView)view.findViewById(R.id.staff_depart);
            viewHolder.deleteBtn.setTag(position);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.name.setText(staff.getName());
        viewHolder.tel.setText(staff.getTel());
        viewHolder.emial.setText(staff.getEmail());
        viewHolder.depart.setText(staff.getDepart());
        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showTipAndDeleteStaff(getContext(),"删除员工将不能恢复！",position,StaffInfoAdapter.this,mData);
            }
        });
        return view;

    }

    class ViewHolder {
        TextView name;
        TextView tel;
        TextView emial;
        TextView depart;
        ImageButton deleteBtn;
    }
}
