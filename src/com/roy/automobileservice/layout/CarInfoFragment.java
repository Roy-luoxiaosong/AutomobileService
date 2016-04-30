package com.roy.automobileservice.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roy.automobileservice.R;

public class CarInfoFragment extends Fragment{
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.car_info_fragment,container,false);
		return view;
	}
	public void refresh(String carName,int carImage,String discribe){
		View visibilityLayout = view.findViewById(R.id.visibility_layout);
		visibilityLayout.setVisibility(View.VISIBLE);
		TextView carNameTextView = (TextView)view.findViewById(R.id.car_name);
		ImageView carImageView = (ImageView)view.findViewById(R.id.car_picture);
		TextView carDiscribeTextView = (TextView)view.findViewById(R.id.car_discribe_info);
	
		carNameTextView.setText(carName);
		carImageView.setBackgroundResource(carImage);
		carDiscribeTextView.setText(discribe);
	}
	
}
