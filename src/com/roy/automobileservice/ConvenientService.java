package com.roy.automobileservice;

import com.roy.automobileservice.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConvenientService extends Activity implements android.view.View.OnClickListener{
	private Button rescueButton;
	private Button ploiceButton;
	private Button trafficButton;
	private Button fireButton;
	
	private Button roadButton;
	private Button customerButton;
	public static void startAction(Context context){
		Intent intent = new Intent(context,ConvenientService.class);
		context.startActivity(intent);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.convenient_service_lt);
		
		 init();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Rescue_tel:
			Utils.showTipAndCall(ConvenientService.this, "120");
			break;
		case R.id.police_tel:
			Utils.showTipAndCall(ConvenientService.this, "110");
			break;
		case R.id.traffic_tel:
			Utils.showTipAndCall(ConvenientService.this, "122");
			break;
		case R.id.fire_tel:
			Utils.showTipAndCall(ConvenientService.this, "119");
			break;
		case R.id.road_help_tel:
			Utils.showTipAndCall(ConvenientService.this, "24 hour rescue telephone","400 650 4680");
			break;
		case R.id.customer_service_tel:
			Utils.showTipAndCall(ConvenientService.this, "24 hour Customer Service Hotline","400 818 1188");
			break;

		default:
			break;
		}
		
	}
	private void init(){
		rescueButton = (Button)findViewById(R.id.Rescue_tel);
		ploiceButton = (Button)findViewById(R.id.police_tel);
		trafficButton = (Button)findViewById(R.id.traffic_tel);
		fireButton = (Button)findViewById(R.id.fire_tel);
		
		roadButton = (Button)findViewById(R.id.road_help_tel);
		customerButton = (Button)findViewById(R.id.customer_service_tel);
		
		rescueButton.setOnClickListener(this);
		ploiceButton.setOnClickListener(this);
		trafficButton.setOnClickListener(this);
		fireButton.setOnClickListener(this);
		
		roadButton.setOnClickListener(this);
		customerButton.setOnClickListener(this);
	}
	
}
