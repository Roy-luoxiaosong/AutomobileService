package com.roy.automobileservice.activity;


import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.InfoItemAdapter;
import com.roy.automobileservice.adapter.MyInfoPagerAdapter;
import com.roy.automobileservice.cls.CubeTransformer;
import com.roy.automobileservice.cls.InfoItem;
import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.utils.GlobalVariable;

public class MyInfoActivity extends BaseActivity{
	public static void startAction(Context context){
		Intent intent = new Intent(context,MyInfoActivity.class);
		context.startActivity(intent);
	}
//	private boolean hasBeautyHistory;
//	private boolean hasReapairHistory;
	private List<InfoItem> infoItems = new ArrayList<InfoItem>();
	private AvatarImageView imgAvatarImageView;
	private ViewPager pager = null;
	private PagerTabStrip tabStrip = null;
	private ArrayList<View> viewList = new ArrayList<View>();
	private ArrayList<String> titleList = new ArrayList<String>();
	private MyInfoPagerAdapter adapter;
	private InfoItemAdapter infoItemAdapter;
	private ListView infoItemListView;
	private View view1,view2,view3,view4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_info_activity_layout);
		init();
	}
	@SuppressWarnings("deprecation")
	private void init(){
		pager = (ViewPager) this.findViewById(R.id.viewpager);
        tabStrip = (PagerTabStrip) this.findViewById(R.id.tabstrip);

        //取消tab下面的长横线
        tabStrip.setDrawFullUnderline(false);
        //设置tab的背景色
        tabStrip.setBackgroundColor(getResources().getColor(R.color.bg));
        //设置当前tab页签的下划线颜色
        tabStrip.setTabIndicatorColor(android.graphics.Color.RED);
        tabStrip.setTextSpacing(100);
        tabStrip.setTextColor(android.graphics.Color.WHITE);
        tabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
      //设置标题
      	TextView textView = (TextView)findViewById(R.id.title_back_text);
      	textView.setText(" ");
        view1 = LayoutInflater.from(this).inflate(R.layout.my_info_layout, null);
        imgAvatarImageView = (AvatarImageView)view1.findViewById(R.id.my_info_haha_avatar_img);
        imgAvatarImageView.setImageResource(GlobalVariable.currentUser.getAvatarImage());
        infoItemListView = (ListView)view1.findViewById(R.id.info_item);
        initItemList();
        infoItemAdapter = new InfoItemAdapter(this, R.layout.info_item, infoItems);
        infoItemListView.setAdapter(infoItemAdapter);
        
        view2 = LayoutInflater.from(this).inflate(R.layout.my_car_info_tab, null);
        view3 = LayoutInflater.from(this).inflate(R.layout.auto_beauty_history_tab, null);
        view4 = LayoutInflater.from(this).inflate(R.layout.auto_repair_history_tab, null);
      //viewpager开始添加view
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
      //页签项
        titleList.add("我的信息");
        titleList.add("我的汽车");
        titleList.add("美容历史");
        titleList.add("维修历史");
        adapter = new MyInfoPagerAdapter(viewList, titleList);
        pager.setPageTransformer(true, new CubeTransformer());
        pager.setAdapter(adapter);
       
        
	}
	private void inithanges(){
		view1 = LayoutInflater.from(this).inflate(R.layout.my_info_layout, null);
        imgAvatarImageView = (AvatarImageView)view1.findViewById(R.id.my_info_haha_avatar_img);
        imgAvatarImageView.setImageResource(GlobalVariable.currentUser.getAvatarImage());
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		inithanges();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		inithanges();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		inithanges();
		
	}
	private void initItemList(){
		infoItems.add(new InfoItem("用户昵称", GlobalVariable.currentUser.getUserName()));
		infoItems.add(new InfoItem("真实姓名",GlobalVariable.currentUser.getRealName()));
		infoItems.add(new InfoItem("性别",GlobalVariable.currentUser.getSex()));
		infoItems.add(new InfoItem("邮箱地址",GlobalVariable.currentUser.getEmail()));
		infoItems.add(new InfoItem("手机号码",GlobalVariable.currentUser.getTelNumber()));
		infoItems.add(new InfoItem("居住地址",GlobalVariable.currentUser.getAddress()));
		infoItems.add(new InfoItem("汽车名字",GlobalVariable.currentUser.getCar().getName()));
	}
	
}
