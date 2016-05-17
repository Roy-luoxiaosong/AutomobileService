package com.roy.automobileservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.activity.CarTemp;
import com.roy.automobileservice.cls.ChinaCar;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roy on 2016/5/9.
 */
public class CarTempAdapter extends ArrayAdapter<ChinaCar> {
    private int resourceId;
    private Context context;
    public CarTempAdapter(Context context, int resource, List<ChinaCar> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChinaCar car = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.carName = (TextView)view.findViewById(R.id.car_name);
            viewHolder.carPicture = (ImageView)view.findViewById(R.id.car_picture_temp);
            viewHolder.carHeat = (TextView)view.findViewById(R.id.car_heat);
            viewHolder.carPrice = (TextView)view.findViewById(R.id.car_price);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.carName.setText(car.getCarName());
        //viewHolder.carPicture.setImageResource(car.getImageIds());
        //new DownloadImage(context,viewHolder.carPicture,car.getImageIds().trim()).execute();
        //viewHolder.carPicture.setImageResource(R.drawable.d_auto_part);
        Picasso.with(context).load(car.getImageIds().get(0)).into(viewHolder.carPicture);
        viewHolder.carHeat.setText(String.valueOf(car.getHeat()));
        viewHolder.carPrice.setText(car.getPrice());
        return view;

    }
    class ViewHolder{
        TextView carName;
        ImageView carPicture;
        TextView carHeat;
        TextView carPrice;
    }
}
