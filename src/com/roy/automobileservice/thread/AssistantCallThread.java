package com.roy.automobileservice.thread;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AssistantCallThread implements Runnable{
	private Context context;
	private String telNumString;
	
	
	public AssistantCallThread(Context context,String telNumString) {
		super();
		this.context = context;
		this.telNumString = telNumString;
	}

	@Override
	public void run() {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:"+telNumString));
		context.startActivity(intent);
		
	}

}
