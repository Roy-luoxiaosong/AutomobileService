package com.roy.automobileservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.layout.CarInfoFragment;
import com.roy.automobileservice.layout.PictureSwitcherFragment;
import com.roy.automobileservice.layout.TitleBackLayout;
import com.roy.automobileservice.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CarInfoActivity extends BaseActivity implements View.OnClickListener{
	private Button calButton;
	private Button calculator;
	public static void actionStart(Context context, String carName, ArrayList<String> carPicture, String carDiscribe){
		Intent intent = new Intent(context,CarInfoActivity.class);
		intent.putExtra("car_name", carName);
		intent.putStringArrayListExtra("car_picture", carPicture);
		intent.putExtra("car_discribe", carDiscribe);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.car_info_activity);
		
		calButton = (Button)findViewById(R.id.cal_for_more_info);
		calculator = (Button)findViewById(R.id.car_calculator);
		
		String carName = getIntent().getStringExtra("car_name");
		ArrayList<String> carPictures = getIntent().getStringArrayListExtra("car_picture");
		String carDiscribe = getIntent().getStringExtra("car_discribe");
		CarInfoFragment carInfoFragment = (CarInfoFragment)getFragmentManager()
				.findFragmentById(R.id.car_info_frag);
		carInfoFragment.refresh(carPictures, carDiscribe);

		PictureSwitcherFragment psf = (PictureSwitcherFragment)getFragmentManager().findFragmentById(R.id.car_pictures);
		psf.setImages(carPictures);
		
		//���ñ���
		TitleBackLayout title  = (TitleBackLayout)findViewById(R.id.car_info_title);
		TextView textView = (TextView)title.findViewById(R.id.title_back_text);
		textView.setText(carName);
		
		calButton.setOnClickListener(this);
		calculator.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cal_for_more_info:
				Utils.showTipAndCall(CarInfoActivity.this,R.string.tip_msg_cal_for_car_info, "08528888888");
				break;
			case  R.id.car_calculator:
				CarCalculatorActivity.startAction(CarInfoActivity.this);
		}
	}
}
