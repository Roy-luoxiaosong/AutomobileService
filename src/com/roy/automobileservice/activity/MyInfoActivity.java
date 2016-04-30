package com.roy.automobileservice.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.roy.automobileservice.*;

public class MyInfoActivity extends Activity{
	public static void startAction(Context context){
		Intent intent = new Intent(context,MyInfoActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_info_activity_layout);
	}
	
}
