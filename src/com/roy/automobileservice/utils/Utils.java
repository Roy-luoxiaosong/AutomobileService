package com.roy.automobileservice.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.roy.automobileservice.AssistantCallThread;

public class Utils {
	public static void showTipAndCall(final Context  context,final String telNum){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("TIP");
		dialog.setMessage("Are you sure to call "+telNum +"?");
		dialog.setCancelable(true);
		dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new Thread(new AssistantCallThread(context, telNum)).start()
				;
			}
		});
		dialog.setNegativeButton("CANCLE", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		});
		dialog.show();
	}
	public static void showTipAndCall(final Context  context,final String tipMsg,final String telNum){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("TIP");
		dialog.setMessage("Are you sure to call "+tipMsg +"?");
		dialog.setCancelable(true);
		dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new Thread(new AssistantCallThread(context, telNum)).start()
				;
			}
		});
		dialog.setNegativeButton("CANCLE", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		});
		dialog.show();
	}
}
