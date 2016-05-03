package com.roy.automobileservice.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {
	public static boolean netState;
	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo!=null&&networkInfo.isAvailable()){
			netState = true;
		}else{
			netState = false;
		}
	}

}
