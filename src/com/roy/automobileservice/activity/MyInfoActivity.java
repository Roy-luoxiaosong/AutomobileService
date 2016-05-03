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

        //ȡ��tab����ĳ�����
        tabStrip.setDrawFullUnderline(false);
        //����tab�ı���ɫ
        tabStrip.setBackgroundColor(getResources().getColor(R.color.bg));
        //���õ�ǰtabҳǩ���»�����ɫ
        tabStrip.setTabIndicatorColor(android.graphics.Color.RED);
        tabStrip.setTextSpacing(100);
        tabStrip.setTextColor(android.graphics.Color.WHITE);
        tabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
      //���ñ���
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
      //viewpager��ʼ���view
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
      //ҳǩ��
        titleList.add("�ҵ���Ϣ");
        titleList.add("�ҵ�����");
        titleList.add("������ʷ");
        titleList.add("ά����ʷ");
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
		infoItems.add(new InfoItem("�û��ǳ�", GlobalVariable.currentUser.getUserName()));
		infoItems.add(new InfoItem("��ʵ����",GlobalVariable.currentUser.getRealName()));
		infoItems.add(new InfoItem("�Ա�",GlobalVariable.currentUser.getSex()));
		infoItems.add(new InfoItem("�����ַ",GlobalVariable.currentUser.getEmail()));
		infoItems.add(new InfoItem("�ֻ�����",GlobalVariable.currentUser.getTelNumber()));
		infoItems.add(new InfoItem("��ס��ַ",GlobalVariable.currentUser.getAddress()));
		infoItems.add(new InfoItem("��������",GlobalVariable.currentUser.getCar().getName()));
	}
	
}
