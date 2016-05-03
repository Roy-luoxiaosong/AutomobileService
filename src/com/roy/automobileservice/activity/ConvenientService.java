package com.roy.automobileservice.activity;

import com.roy.automobileservice.R;
import com.roy.automobileservice.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class ConvenientService extends BaseActivity implements android.view.View.OnClickListener{
	private LinearLayout rescueButton;
	private LinearLayout ploiceButton;
	private LinearLayout trafficButton;
	private LinearLayout fireButton;
	
	private LinearLayout roadButton;
	private LinearLayout customerButton;
	public static void startAction(Context context){
		Intent intent = new Intent(context,ConvenientService.class);
		context.startActivity(intent);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.convenient_service_lt);
		
		 init();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Rescue_tel:
			Utils.showTipAndCall(ConvenientService.this, R.string.tip_msg_text_120,"120");
			break;
		case R.id.police_tel:
			Utils.showTipAndCall(ConvenientService.this, R.string.tip_msg_text_110,"110");
			break;
		case R.id.traffic_tel:
			Utils.showTipAndCall(ConvenientService.this, R.string.tip_msg_text_122,"122");
			break;
		case R.id.fire_tel:
			Utils.showTipAndCall(ConvenientService.this, R.string.tip_msg_text_119,"119");
			break;
		case R.id.road_help_tel:
			Utils.showTipAndCall(ConvenientService.this,R.string.tip_msg_rescue_text,"400 650 4680");
			break;
		case R.id.customer_service_tel:
			Utils.showTipAndCall(ConvenientService.this, R.string.tip_msg_cs_text,"400 818 1188");
			break;

		default:
			break;
		}
		
	}
	private void init(){
		rescueButton = (LinearLayout)findViewById(R.id.Rescue_tel);
		ploiceButton = (LinearLayout)findViewById(R.id.police_tel);
		trafficButton = (LinearLayout)findViewById(R.id.traffic_tel);
		fireButton = (LinearLayout)findViewById(R.id.fire_tel);
		
		roadButton = (LinearLayout)findViewById(R.id.road_help_tel);
		customerButton = (LinearLayout)findViewById(R.id.customer_service_tel);
		
		rescueButton.setOnClickListener(this);
		ploiceButton.setOnClickListener(this);
		trafficButton.setOnClickListener(this);
		fireButton.setOnClickListener(this);
		
		roadButton.setOnClickListener(this);
		customerButton.setOnClickListener(this);
	}
	
}
