package com.roy.automobileservice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;
import com.roy.automobileservice.*;

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
