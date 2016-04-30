package com.roy.automobileservice.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.ViewPagerAdapter;
import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.utils.Utils;

public class HomePageActivity extends Activity implements android.view.View.OnClickListener,ViewPager.OnPageChangeListener{
	
	public static void startAction(Context context){
		Intent intent = new Intent(context,HomePageActivity.class);
		context.startActivity(intent);
	}
	private AvatarImageView userImage;
	private Button myInfoButton;
	private Button autoBeautyButton;
	private Button autoRepairButton;
	private Button autoPartButton;
	private Button roadAssisButton;
	private Button carModelsButton;
	
	private Button loginButton;
	
	private ViewPager mViewPaper;
	private List<ImageView> images;
	private List<View> dots;
	private int currentItem;
	//记录上一次点的位置
	private int oldPosition = 0;
	//存放图片的id
	private int[] imageIds;
	//存放图片的标题
	private String[]  titles;
	private TextView title;
	private ViewPagerAdapter adapter;
	private ScheduledExecutorService scheduledExecutorService;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_page_view);
		
		
		
		init();
		
		adapter = new ViewPagerAdapter(images);
		mViewPaper.setAdapter(adapter);
		
		mViewPaper.setOnPageChangeListener(this);
		if(LoginActivity.isLogin){
			loginButton.setText(R.string.exit_button);
		}
	}

	private void init(){
		//存放图片的id
		 imageIds = new int[]{
				R.drawable.a_my_car,
				R.drawable.b_auto_beauty,
				R.drawable.c_auto_repair,
				R.drawable.d_auto_part,
				R.drawable.e_road_assistant
		};
		//存放图片的标题
	  titles = new String[]{
	        	"汽车风貌",	
	        	"汽车美容",	
	        	"汽车维修",	
	        	"汽车备件",	
	        	"道路救援"
	        };
	  mViewPaper = (ViewPager) findViewById(R.id.vp);
		
		//显示的图片
		images = new ArrayList<ImageView>();
		for(int i = 0; i < imageIds.length; i++){
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(imageIds[i]);
			images.add(imageView);
		}
		
		myInfoButton = (Button)findViewById(R.id.my_info_option);
		autoBeautyButton = (Button)findViewById(R.id.auto_beauty_option);
		autoRepairButton = (Button)findViewById(R.id.auto_repair_option);
		autoPartButton = (Button)findViewById(R.id.auto_part_option);
		roadAssisButton = (Button)findViewById(R.id.road_assis_option);
		carModelsButton = (Button)findViewById(R.id.car_models_option);
		userImage = (AvatarImageView)findViewById(R.id.login_avatar_img);
		
		loginButton = (Button)findViewById(R.id.login_title_button);		
		myInfoButton.setOnClickListener(this);
		autoBeautyButton.setOnClickListener(this);
		autoRepairButton.setOnClickListener(this);
		autoPartButton.setOnClickListener(this);
		roadAssisButton.setOnClickListener(this);
		carModelsButton.setOnClickListener(this);
		
		loginButton.setOnClickListener(this);
		
		//显示的小点
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot_0));
		dots.add(findViewById(R.id.dot_1));
		dots.add(findViewById(R.id.dot_2));
		dots.add(findViewById(R.id.dot_3));
		dots.add(findViewById(R.id.dot_4));
		
		title = (TextView) findViewById(R.id.title);
		title.setText(titles[0]);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_info_option:
			if(!LoginActivity.isLogin){
				Utils.showTipAndLogin(HomePageActivity.this, R.string.tip_msg_to_login_text);
			}else{
				MyInfoActivity.startAction(HomePageActivity.this);
			}
			break;
		case R.id.auto_beauty_option:
			Toast.makeText(HomePageActivity.this, "you clicked autoBeauty button", Toast.LENGTH_SHORT).show();
			break;
		case R.id.auto_repair_option:
			Toast.makeText(HomePageActivity.this, "you clicked autoRepair button", Toast.LENGTH_SHORT).show();
			break;
		case R.id.auto_part_option:
			Toast.makeText(HomePageActivity.this, "you clicked autoPart button", Toast.LENGTH_SHORT).show();
			break;
		case R.id.login_title_button:
			String str = loginButton.getText().toString();
			if(str.equals("exit")||str.equals("退出")){
				loginButton.setText(R.string.login_button);
				userImage.setImageResource(R.drawable.user_default_icon);
				LoginActivity.isLogin = false;
			}else{
				LoginActivity.startAction(HomePageActivity.this);
				
			}
			Toast.makeText(HomePageActivity.this, "you clicked login button", Toast.LENGTH_SHORT).show();
			break;
		case R.id.road_assis_option:
			ConvenientService.startAction(HomePageActivity.this);
			break;
		case R.id.car_models_option:
			CarModelsListActivity.startAction(HomePageActivity.this);
			break;
		default:
			break;
		}
		
	}
	/**
	 * 利用线程池定时执行动画轮播
	 */
	@Override
	protected void onStart() {
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleWithFixedDelay(
				new ViewPageTask(), 
				2, 
				2, 
				TimeUnit.SECONDS);
		refreshTitleContent();
	}
	
	
	private class ViewPageTask implements Runnable{

		@Override
		public void run() {
			currentItem = (currentItem + 1) % imageIds.length;
			mHandler.sendEmptyMessage(0);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 接收子线程传递过来的数据
	 */
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			mViewPaper.setCurrentItem(currentItem);
		};
	};
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	public void onPageSelected(int position) {
		title.setText(titles[position]);
		dots.get(position).setBackgroundResource(R.drawable.dot_focused);
		dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
		
		oldPosition = position;
		currentItem = position;
	}
	
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		fileList();
		LoginActivity.isLogin = false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		refreshTitleContent();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		refreshTitleContent();
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshTitleContent();
	}
	
	private void refreshTitleContent(){
		if(LoginActivity.isLogin){
			loginButton.setText(R.string.exit_button);
			if(LoginActivity.curUserNameItem!=null&&LoginActivity.curUserNameItem.getAvatarImage()>0){
				userImage.setImageResource(LoginActivity.curUserNameItem.getAvatarImage());
			}
		}else{
			loginButton.setText(R.string.login_button);
			userImage.setImageResource(R.drawable.user_default_icon);
		}
	}
	

}
