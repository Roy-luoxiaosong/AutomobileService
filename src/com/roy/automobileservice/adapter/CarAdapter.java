package com.roy.automobileservice.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.Car;


public class CarAdapter extends ArrayAdapter<Car>{
	private int resourceId;
	
	public CarAdapter(Context context, int resource, List<Car> objects) {
		super(context, resource, objects);
		this.resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 Car car = getItem(position);
		 View view;
		 ViewHolder viewHolder;
		 if(convertView==null){
			 view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			 viewHolder = new ViewHolder();
			 viewHolder.carName = (TextView)view.findViewById(R.id.car_name);
			 viewHolder.carPicture = (ImageView)view.findViewById(R.id.car_picture);
			 viewHolder.carHeat = (TextView)view.findViewById(R.id.car_heat);
			 viewHolder.carPrice = (TextView)view.findViewById(R.id.car_price);
			 view.setTag(viewHolder);
		 }else{
			 view = convertView;
			 viewHolder = (ViewHolder)view.getTag();
		 }
		 viewHolder.carName.setText(car.getName());
		 viewHolder.carPicture.setImageResource(car.getImageId());
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
