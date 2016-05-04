package com.roy.automobileservice.activity;



import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.broadcast.NetworkChangeReceiver;
import com.roy.automobileservice.cls.ActivityCollector;
import com.roy.automobileservice.cls.Car;
import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.utils.GlobalVariable;
import com.roy.automobileservice.utils.TestData;
import com.roy.automobileservice.utils.Utils;

public class HomePageActivity extends BaseActivity implements android.view.View.OnClickListener{
	
	public static void startAction(Context context){
		Intent intent = new Intent(context,HomePageActivity.class);
		context.startActivity(intent);
	}
	private AvatarImageView userImage;
	private LinearLayout myInfoButton;
	private LinearLayout autoBeautyButton;
	private LinearLayout autoRepairButton;
	private LinearLayout autoPartButton;
	private LinearLayout roadAssisButton;
	private LinearLayout carModelsButton;
	private NetworkChangeReceiver networkChangeReceiver;
	private IntentFilter intentFilter;
	private RelativeLayout noNetNotifictionLayout;
	
	private Button loginButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_page_view);
		init();
		registerReceiver(networkChangeReceiver, intentFilter);
		if(LoginActivity.isLogin){
			loginButton.setText(R.string.exit_button);
		}
	}

	private void init(){
		getCars();
		TestData.initUserTest(HomePageActivity.this);
		networkChangeReceiver = new NetworkChangeReceiver();
		intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		
		myInfoButton = (LinearLayout)findViewById(R.id.my_info_menu);
		autoBeautyButton = (LinearLayout)findViewById(R.id.auto_beauty_menu);
		autoRepairButton = (LinearLayout)findViewById(R.id.auto_repair_menu);
		autoPartButton = (LinearLayout)findViewById(R.id.auto_part_menu);
		roadAssisButton = (LinearLayout)findViewById(R.id.road_assis_menu);
		carModelsButton = (LinearLayout)findViewById(R.id.car_models_menu);
		userImage = (AvatarImageView)findViewById(R.id.login_avatar_img);
		noNetNotifictionLayout = (RelativeLayout)findViewById(R.id.net_view_r1);
		
		loginButton = (Button)findViewById(R.id.login_title_button);		
		myInfoButton.setOnClickListener(this);
		autoBeautyButton.setOnClickListener(this);
		autoRepairButton.setOnClickListener(this);
		autoPartButton.setOnClickListener(this);
		roadAssisButton.setOnClickListener(this);
		carModelsButton.setOnClickListener(this);
		
		loginButton.setOnClickListener(this);
		noNetNotifictionLayout.setOnClickListener(this);
		
	
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_info_menu:
			if(!LoginActivity.isLogin){
				Utils.showTipAndLogin(HomePageActivity.this, R.string.tip_msg_to_login_text);
			}else{
				MyInfoActivity.startAction(HomePageActivity.this);
			}
			break;
		case R.id.auto_beauty_menu:
			Toast.makeText(HomePageActivity.this, "you clicked autoBeauty button", Toast.LENGTH_SHORT).show();
			break;
		case R.id.auto_repair_menu:
			Toast.makeText(HomePageActivity.this, "you clicked autoRepair button", Toast.LENGTH_SHORT).show();
			break;
		case R.id.auto_part_menu:
			Toast.makeText(HomePageActivity.this, "you clicked autoPart button", Toast.LENGTH_SHORT).show();
			break;
		case R.id.login_title_button:
			String str = loginButton.getText().toString();
			if(str.equals("EXIT")||str.equals("退出")){
				loginButton.setText(R.string.login_button);
				userImage.setImageResource(R.drawable.user_default_icon1);
				GlobalVariable.currentUser = null;
				LoginActivity.isLogin = false;
			}else{
				LoginActivity.startAction(HomePageActivity.this);
				
			}
			break;
		case R.id.road_assis_menu:
			ConvenientService.startAction(HomePageActivity.this);
			break;
		case R.id.car_models_menu:
			CarModelsListActivity.startAction(HomePageActivity.this);
			break;
		case R.id.net_view_r1:
			if(noNetNotifictionLayout!=null){
				Intent intent = new Intent("android.settings.SETTINGS");
				startActivity(intent);
			}
		default:
			break;
		}
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		noNetNotifictionLayout.setVisibility(NetworkChangeReceiver.netState?View.GONE:View.VISIBLE);
		refreshTitleContent();
	}
	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		LoginActivity.isLogin = false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		noNetNotifictionLayout.setVisibility(NetworkChangeReceiver.netState?View.GONE:View.VISIBLE);
		refreshTitleContent();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		noNetNotifictionLayout.setVisibility(NetworkChangeReceiver.netState?View.GONE:View.VISIBLE);
		refreshTitleContent();
	}

	@Override
	protected void onResume() {
		super.onResume();
		noNetNotifictionLayout.setVisibility(NetworkChangeReceiver.netState?View.GONE:View.VISIBLE);
		refreshTitleContent();
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(networkChangeReceiver);
	}

	private void refreshTitleContent(){
		if(LoginActivity.isLogin){
			loginButton.setText(R.string.exit_button);
			if(!TextUtils.isEmpty(GlobalVariable.currentUser.getUserName())&&GlobalVariable.currentUser.getAvatarImage()>0){
				userImage.setImageResource(GlobalVariable.currentUser.getAvatarImage());
			}
		}else{
			loginButton.setText(R.string.login_button);
			userImage.setImageResource(R.drawable.user_default_icon1);
		}
	}
	//初始化汽车数据
	private void getCars(){
		for(int i=1;i<20;i++){
			Car car = new Car(getResources().getString(R.string.test_car_name)+i,R.drawable.b_auto_beauty,
					getResources().getString(R.string.test_car_discribute)+i+"ninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasjninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasflajfkldsajfakfjasljfdasklfjalkfjalkfdjkasfjalfjak辆车");
			car.setHeat(20);
			car.setPrice("20万");
			CarModelsListActivity.carList.add(car);
			
		}
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {  
		    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {  
		      exitApp();  
		    }  
		    return true;  
		  }  
		return super.dispatchKeyEvent(event);
	}
	private void exitApp() {  
		  
		// 判断2次点击事件时间  
		  if ((System.currentTimeMillis() - exitTime) > 2000) {  
		    Toast.makeText(this, R.string.notifiction_to_exit_app, Toast.LENGTH_SHORT).show();  
		    exitTime = System.currentTimeMillis();  
		  } else {  
		    ActivityCollector.finishAll();
		    LoginActivity.isLogin = false;
		  }  
	}
	

	

}
