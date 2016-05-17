package com.roy.automobileservice.layout;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarInfoFragment extends Fragment{
	private View view;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.car_info_fragment,container,false);
		return view;
	}
	public void refresh(ArrayList<String> carImages,String discribe){
		View visibilityLayout = view.findViewById(R.id.visibility_layout);
		visibilityLayout.setVisibility(View.VISIBLE);

		TextView carDiscribeTextView = (TextView)view.findViewById(R.id.car_discribe_info);

		/*PictureSwitcherFragment psf= (PictureSwitcherFragment)getFragmentManager().findFragmentById(R.id.car_pictures);
		psf.setImages(carImages);*/
	

		carDiscribeTextView.setText(discribe);
	}
	
}
