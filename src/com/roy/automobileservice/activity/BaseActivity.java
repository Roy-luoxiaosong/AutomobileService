package com.roy.automobileservice.activity;

import android.app.Activity;
import android.os.Bundle;
import com.roy.automobileservice.cls.ActivityCollector;

public class BaseActivity extends Activity{
	long exitTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	
		
	
}
