package com.roy.automobileservice.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.roy.automobileservice.R;
import com.roy.automobileservice.activity.LoginActivity;
import com.roy.automobileservice.adapter.UserNameAdapter;
import com.roy.automobileservice.thread.AssistantCallThread;

public class Utils {
	public static void showTipAndCall(final Context  context,final int tipMsg,final String telNum){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		setDialogContent(dialog,tipMsg);
		dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new Thread(new AssistantCallThread(context, telNum)).start()
				;
			}
		});
		
		dialog.show();
	}
	public static void showTipAndLogin(final Context  context,final int tipMsg){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		setDialogContent(dialog,tipMsg);
		dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LoginActivity.startAction(context);
			}
		});
		
		dialog.show();
	}
	public static void showTipAndDeleteUer(final Context  context,final int tipMsg,final Integer position,final UserNameAdapter userNameAdapter){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		setDialogContent(dialog,tipMsg);
		dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				UserNameAdapter.list.remove(Integer.valueOf(position).intValue());
				userNameAdapter.notifyDataSetChanged();
			}
		});
		
		dialog.show();
	}
	private static void setDialogContent(AlertDialog.Builder dialog,int tipMsg){
		dialog.setTitle(R.string.tip_text);
		dialog.setMessage(tipMsg);
		dialog.setCancelable(true);
		dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		});
	}
	
}
