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

import android.widget.RelativeLayout;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.broadcast.NetworkChangeReceiver;
import com.roy.automobileservice.cls.ActivityCollector;

import com.roy.automobileservice.cls.Car;
import com.roy.automobileservice.cls.HeadSculpture;

import com.roy.automobileservice.cls.User;

import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.layout.CircleMenuLayout;
import com.roy.automobileservice.layout.CircleMenuLayout.OnMenuItemClickListener;
import com.roy.automobileservice.utils.GlobalVariable;
import com.roy.automobileservice.utils.TestData;
import com.roy.automobileservice.utils.Utils;



import java.util.List;

import cn.bmob.v3.Bmob;



public class HomePageActivity extends BaseActivity implements android.view.View.OnClickListener{
	private final static int MY_INFO = R.drawable.my_info_option_bg;
	private final static int AUTO_BEAUTY =R.drawable.auto_beauty_option_bg;
	private final static int AUTO_REPAIR =R.drawable.auto_repair_option_bg;
	private final static int AUTO_PART =R.drawable.auto_part_option_bg;
	private final static int AUTO_ROAD =R.drawable.road_assistant_option_bg;
	private final static int CAR_MODLE =R.drawable.car_models_option_bg;
	
	public static void startAction(Context context){
		Intent intent = new Intent(context,HomePageActivity.class);
		context.startActivity(intent);
	}


	public static List<CarTemp> carTemps;

	private AvatarImageView userImage;
	private CircleMenuLayout mCircleMenuLayout;
	private String[] itemTexts = new String[6];

	private int[] itemImagIds = new int[6];
	private NetworkChangeReceiver networkChangeReceiver;
	private IntentFilter intentFilter;

	private RelativeLayout noNetNotifictionLayout;
	private Button loginButton;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_page_view);

		Bmob.initialize(this, "811186c53ef97fe8b6579c684e61b053");

		initMenuItems();
		init();


		registerReceiver(networkChangeReceiver, intentFilter);
		if(LoginActivity.isLogin){
			loginButton.setText(R.string.exit_button);
		}
	}

	private void init(){
		initUserTest(HomePageActivity.this);
		networkChangeReceiver = new NetworkChangeReceiver();
		intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		

		userImage = (AvatarImageView)findViewById(R.id.login_avatar_img);
		noNetNotifictionLayout = (RelativeLayout)findViewById(R.id.net_view_r1);
		
		loginButton = (Button)findViewById(R.id.login_title_button);		

		
		loginButton.setOnClickListener(this);
		noNetNotifictionLayout.setOnClickListener(this);
		
	
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

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
		Utils.deleteStaticVariables();
		//Log.d("tag","销毁后的长度是："+carTemps.size());
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
		  
		//
		  if ((System.currentTimeMillis() - exitTime) > 2000) {  
		    Toast.makeText(this, R.string.notifiction_to_exit_app, Toast.LENGTH_SHORT).show();  
		    exitTime = System.currentTimeMillis();  
		  } else {  
		    ActivityCollector.finishAll();
		    LoginActivity.isLogin = false;
		  }  
	}
	private void initUserTest(Context context){

        for(int i=1;i<20;i++){
			Car car = new Car(context.getResources().getString(R.string.test_car_name)+i,R.drawable.b_auto_beauty,
					context.getResources().getString(R.string.test_car_discribute)+i+"ninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasjninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasflajfkldsajfakfjasljfdasklfjalkfjalkfdjkasfjalfjak����");
			car.setHeat(20);
			car.setPrice(context.getResources().getString(R.string.test_car_value));
			TestData.carList.add(car);

		}



		User userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user1_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon1);
        userNameItem.setCar(TestData.carList.get(0));
        userNameItem.setEmail("user1@qq.com");
        userNameItem.setPassword("user1");
        userNameItem.setRealName(context.getResources().getString(R.string.user1_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user1_sex));
        userNameItem.setTelNumber("15062356840");
        TestData.userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user2_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon2);
        userNameItem.setCar(TestData.carList.get(2));
        userNameItem.setEmail("user2@qq.com");
        userNameItem.setPassword("user2");
        userNameItem.setRealName(context.getResources().getString(R.string.user2_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user2_sex));
        userNameItem.setTelNumber("18665987586");
        TestData.userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user3_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon3);
        userNameItem.setCar(null);
        userNameItem.setEmail("user3@qq.com");
        userNameItem.setPassword("user3");
        userNameItem.setRealName(context.getResources().getString(R.string.user3_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user3_sex));
        userNameItem.setTelNumber("13556789564");
        TestData.userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user4_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon4);
        userNameItem.setCar(TestData.carList.get(6));
        userNameItem.setEmail("user4@qq.com");
        userNameItem.setPassword("user4");
        userNameItem.setRealName(context.getResources().getString(R.string.user4_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user4_sex));
        userNameItem.setTelNumber("14156942536");
        TestData.userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user5_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon5);
        userNameItem.setCar(TestData.carList.get(10));
        userNameItem.setEmail("user5@qq.com");
        userNameItem.setPassword("user5");
        userNameItem.setRealName(context.getResources().getString(R.string.user5_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user5_sex));
        userNameItem.setTelNumber("16253462358");
        TestData.userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user6_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon6);
        userNameItem.setCar(TestData.carList.get(5));
        userNameItem.setEmail("user6@qq.com");
        userNameItem.setPassword("user6");
        userNameItem.setRealName(context.getResources().getString(R.string.user6_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user6_sex));
        userNameItem.setTelNumber("14123569871");
        TestData.userTestList.add(userNameItem);
        
        HeadSculpture temSculpture = new HeadSculpture(R.drawable.user_default_icon1,
        		context.getResources().getString(R.string.default_cion1_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon2, 
        		context.getResources().getString(R.string.default_cion2_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon3, 
        		context.getResources().getString(R.string.default_cion3_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon4,
        		context.getResources().getString(R.string.default_cion4_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon5, 
        		context.getResources().getString(R.string.default_cion5_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon6, 
        		context.getResources().getString(R.string.default_cion6_name));
        TestData.headSculpturesList.add(temSculpture);
        
	}
	

	private void initMenuItems(){
		itemTexts = new String[] { getResources().getString(R.string.my_info_option_text), 
								getResources().getString(R.string.auto_beauty_option_text), 
								getResources().getString(R.string.auto_repair_option_text),  
								getResources().getString(R.string.auto_part_option_text),
								getResources().getString(R.string.road_assis_option_text), 
								getResources().getString(R.string.car_models_text)};  
		itemImagIds = new int[] { MY_INFO,
								AUTO_BEAUTY, 
								AUTO_REPAIR,
								AUTO_PART, 
								AUTO_ROAD,
								CAR_MODLE};
		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(itemImagIds, itemTexts);
		
		

		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			
			@Override
			public void itemClick(View view, int pos)
			{
//				Toast.makeText(HomePageActivity.this, itemTexts[pos],
//						Toast.LENGTH_SHORT).show();
				switch (itemImagIds[pos]) {
				case MY_INFO:
				if(!LoginActivity.isLogin){
				Utils.showTipAndLogin(HomePageActivity.this, R.string.tip_msg_to_login_text);
			}else{
				MyInfoActivity.startAction(HomePageActivity.this);
			}
			//MyInfoActivity.startAction(HomePageActivity.this);
					break;
				case AUTO_BEAUTY:
					//Toast.makeText(HomePageActivity.this, "you clicked autoBeauty button", Toast.LENGTH_SHORT).show();
					//TestActivity.startAction(HomePageActivity.this);
					CarBeautyActivity.startAction(HomePageActivity.this);
					break;
				case AUTO_REPAIR:
					//Toast.makeText(HomePageActivity.this, "you clicked autoRepair button", Toast.LENGTH_SHORT).show();
					CarRepairActivity.startAction(HomePageActivity.this);
					break;
				case AUTO_PART:
					//Toast.makeText(HomePageActivity.this, "you clicked autoPart button", Toast.LENGTH_SHORT).show();
					//MapActivity.startAction(HomePageActivity.this);
					break;
				case AUTO_ROAD:
					ConvenientService.startAction(HomePageActivity.this);
					break;
				case CAR_MODLE:
					CarModelsListActivity.startAction(HomePageActivity.this);
					break;

				default:
					break;
				}

			}
			
			@Override
			public void itemCenterClick(View view)
			{
//				Toast.makeText(HomePageActivity.this,
//						"you can do something just like ccb  ",
//						Toast.LENGTH_SHORT).show();
//				
			}
		});
		
	}

}
