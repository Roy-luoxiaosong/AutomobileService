package com.roy.automobileservice.activity;

import com.roy.automobileservice.R;
import com.roy.automobileservice.layout.CarInfoFragment;
import com.roy.automobileservice.layout.TitleBackLayout;
import com.roy.automobileservice.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CarInfoActivity extends Activity{
	private Button calButton;
	public static void actionStart(Context context,String carName,int carPicture,String carDiscribe){
		Intent intent = new Intent(context,CarInfoActivity.class);
		intent.putExtra("car_name", carName);
		intent.putExtra("car_picture", carPicture);
		intent.putExtra("car_discribe", carDiscribe);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.car_info_activity);
		
		calButton = (Button)findViewById(R.id.cal_for_more_info);
		
		String carName = getIntent().getStringExtra("car_name");
		int carPicture = getIntent().getIntExtra("car_picture", 0);
		String carDiscribe = getIntent().getStringExtra("car_discribe");
		CarInfoFragment carInfoFragment = (CarInfoFragment)getFragmentManager()
				.findFragmentById(R.id.car_info_frag);
		carInfoFragment.refresh(carName, carPicture, carDiscribe);
		
		//…Ë÷√±ÍÃ‚
		TitleBackLayout title  = (TitleBackLayout)findViewById(R.id.car_info_title);
		TextView textView = (TextView)title.findViewById(R.id.title_back_text);
		textView.setText(carName);
		
		calButton.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Utils.showTipAndCall(CarInfoActivity.this,R.string.tip_msg_cal_for_car_info, "08528888888");
			}
		});
	}
	
	
}
