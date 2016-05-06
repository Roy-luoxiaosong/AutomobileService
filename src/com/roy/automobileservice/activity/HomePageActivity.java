package com.roy.automobileservice.activity;



import java.io.IOException;



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
import com.roy.automobileservice.cls.HeadSculpture;
import com.roy.automobileservice.cls.User;
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
		//AVOSCloud.initialize(this, "lplzKMKBu0zYcsYCORqGszqH-gzGzoHsz", "Pjc70oYT9mRz5gKquyPnN17D");
		init();
		
		/*try {
			Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		registerReceiver(networkChangeReceiver, intentFilter);
		if(LoginActivity.isLogin){
			loginButton.setText(R.string.exit_button);
		}
		//AVAnalytics.trackAppOpened(getIntent());
		
	}

	private void init(){
		initUserTest(HomePageActivity.this);
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
			//登录验证，暂时去掉
//			if(!LoginActivity.isLogin){
//				Utils.showTipAndLogin(HomePageActivity.this, R.string.tip_msg_to_login_text);
//			}else{
//				MyInfoActivity.startAction(HomePageActivity.this);
//			}
			MyInfoActivity.startAction(HomePageActivity.this);
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
		Utils.deleteStaticVariables();
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
		  
		// 判断2次点击事件时间  
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
					context.getResources().getString(R.string.test_car_discribute)+i+"ninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasjninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasflajfkldsajfakfjasljfdasklfjalkfjalkfdjkasfjalfjak辆车");
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
	

	

}
