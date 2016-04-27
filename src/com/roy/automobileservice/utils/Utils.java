package com.roy.automobileservice.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.roy.automobileservice.AssistantCallThread;
import com.roy.automobileservice.R;

public class Utils {
	public static void showTipAndCall(final Context  context,final int tipMsg,final String telNum){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(R.string.tip_text);
		dialog.setMessage(tipMsg);
		dialog.setCancelable(true);
		dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new Thread(new AssistantCallThread(context, telNum)).start()
				;
			}
		});
		dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		});
		dialog.show();
	}
}
