package com.roy.automobileservice.layout;

import com.roy.automobileservice.R;
import com.roy.automobileservice.activity.HomePageActivity;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class TitleBackLayout extends LinearLayout{

	public TitleBackLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.title_back, this);
		Button titleBack = (Button)findViewById(R.id.title_back_button);
		Button backHome = (Button)findViewById(R.id.title_back_to_home);
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Activity)getContext()).finish();
			}
		});
		backHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HomePageActivity.startAction(getContext());
			}
		});
	}
	

}
