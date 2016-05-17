package com.roy.automobileservice.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.activity.CarCalculatorActivity;
import com.roy.automobileservice.activity.HomePageActivity;
import com.roy.automobileservice.activity.LoginActivity;
import com.roy.automobileservice.activity.RegisterActivity;
import com.roy.automobileservice.adapter.UserNameAdapter;
import com.roy.automobileservice.cls.ActivityCollector;
import com.roy.automobileservice.cls.User;
import com.roy.automobileservice.thread.AssistantCallThread;

public class Utils {
	public static final int HOME_PAGE_REQUEST_CODE = 1;
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
				TestData.userTestList.remove(Integer.valueOf(position).intValue());
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
	public static void showBackToLogin(final Activity  context,final String tipMsg){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		//setDialogContent(dialog,tipMsg);
		dialog.setTitle(R.string.tip_text);
		dialog.setMessage(tipMsg);
		dialog.setCancelable(true);
		dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ActivityCollector.removeActivity(context);
			}
		});
		dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		});
		
		dialog.show();
	}
	public static void showBackToMyInfo(final Activity  context,final String tipMsg){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		//setDialogContent(dialog,tipMsg);
		dialog.setTitle(R.string.tip_text);
		dialog.setMessage(tipMsg);
		dialog.setCancelable(true);
		dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ActivityCollector.removeActivity(context);
			}
		});
		dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		});
		
		dialog.show();
	}

	public static void showCarorderInfo(final Activity  context,final String tipMsg){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		//setDialogContent(dialog,tipMsg);
		dialog.setTitle(R.string.tip_text);
		dialog.setMessage(tipMsg);
		dialog.setCancelable(true);
		dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(context,"订单提交成功",Toast.LENGTH_SHORT).show();
			}
		});
		dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}

		});

		dialog.show();
	}
	public static void showLoRegister(final Activity  context,final String tipMsg){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		//setDialogContent(dialog,tipMsg);
		dialog.setTitle(R.string.tip_text);
		dialog.setMessage(tipMsg);
		dialog.setCancelable(true);
		dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				RegisterActivity.startAction(context);
			}
		});
		dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		});
		
		dialog.show();
	}
	/**
	 * @param name
	 * @return
	 */
	public static  boolean isContainOfName(String name){
    	for(User tem:TestData.userTestList){
    		if(tem.getUserName().equals(name)){
    			return true;
    		}
    	}
		return false;
    }
	/**
	 * @param target
	 * @param len
	 * @return
	 */
	public static List<User> getBackRestraintLenthOfUserList(List<User> target,int len){
		List<User> tem = new ArrayList<User>();
		int targetSize = target.size();
		if(len>targetSize){
			len = targetSize;
		}
		for(int i = target.size()-1;i>=targetSize-len;i--){
			tem.add(target.get(i)); 
		}
		return tem;
	}
/**
 * @param editText
 * @param hintMsg
 * @param hintSize
 */
	public static  void setEditTextHintSize(EditText editText,String hintMsg,int hintSize){
		SpannableString ss = new SpannableString(hintMsg);
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(hintSize,true);
		ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		editText.setHint(new SpannedString(ss)); //
	}
	
	public static void deleteStaticVariables(){
		TestData.carList.clear();
		TestData.headSculpturesList.clear();
		TestData.userTestList.clear();
		//HomePageActivity.carTemps.clear();
		//GlobalVariable.currentUser = new User();
	}
}
