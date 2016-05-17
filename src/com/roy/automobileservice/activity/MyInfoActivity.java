package com.roy.automobileservice.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.InfoItemAdapter;
import com.roy.automobileservice.adapter.MyInfoPagerAdapter;
import com.roy.automobileservice.adapter.ReapirAndBeautyHistoryAdapter;
import com.roy.automobileservice.cls.CubeTransformer;
import com.roy.automobileservice.cls.InfoItem;
import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.utils.GlobalVariable;

public class MyInfoActivity extends BaseActivity implements OnClickListener{
	public static void startAction(Context context){
		Intent intent = new Intent(context,MyInfoActivity.class);
		context.startActivity(intent);
	}
//	private boolean hasBeautyHistory;
//	private boolean hasReapairHistory;

	private ExpandableListView repairHistory;
	private ExpandableListView beautyHistory;
	private ReapirAndBeautyHistoryAdapter repairHistoryAdapter;
	private ReapirAndBeautyHistoryAdapter beautyHistoryAdapter;
	private List<String> repairTitle;
	private Map<String,List<String>> repairContent;
	private List<String> beautyTitle;
	private Map<String, List<String>> beautyContent;
	
	private List<InfoItem> infoItems = new ArrayList<InfoItem>();
	private AvatarImageView imgAvatarImageView;
	private ViewPager pager = null;
	private PagerTabStrip tabStrip = null;
	private ArrayList<View> viewList = new ArrayList<View>();
	private ArrayList<String> titleList = new ArrayList<String>();
	private MyInfoPagerAdapter adapter;
	private InfoItemAdapter infoItemAdapter;
	private ListView infoItemListView;
	private View view1,view2,view3,view4,view5;
	private Button modifyInfo;

	//view2
	private LinearLayout noCarLayout;
	private LinearLayout haveCarLayout;
	private ImageView carImage;
	private TextView buyCarTime;
	private TextView myCarInfo;
	private Button buyNewCar;
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
        
        //设置标题
      	TextView textView = (TextView)findViewById(R.id.title_back_text);
      	textView.setText(" ");
      	
      	//初始化view1
        view1 = LayoutInflater.from(this).inflate(R.layout.my_info_layout, null);
        imgAvatarImageView = (AvatarImageView)view1.findViewById(R.id.my_info_haha_avatar_img);
        imgAvatarImageView.setImageResource(GlobalVariable.currentUser.getAvatarImage());
        infoItemListView = (ListView)view1.findViewById(R.id.info_item);
        initItemList();
        infoItemAdapter = new InfoItemAdapter(this, R.layout.info_item, infoItems);
        infoItemListView.setAdapter(infoItemAdapter);
        modifyInfo = (Button)view1.findViewById(R.id.modify_info_button);
        modifyInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InfoModifyActivity.startAction(MyInfoActivity.this);
			}
		});
        
        //初始化view2
        view2 = LayoutInflater.from(this).inflate(R.layout.my_car_info_tab, null);
		carImage = (ImageView)view2.findViewById(R.id.my_car_picture);
		buyCarTime = (TextView)view2.findViewById(R.id.buy_car_time);
		myCarInfo = (TextView)view2.findViewById(R.id.my_car_discribe_info);
		noCarLayout = (LinearLayout)view2.findViewById(R.id.no_car_layout);
		haveCarLayout = (LinearLayout)view2.findViewById(R.id.have_car_layout);
		buyNewCar = (Button)view2.findViewById(R.id.buy_a_new_car);
		if(GlobalVariable.currentUser.getCar()!=null){
			haveCarLayout.setVisibility(View.VISIBLE);
			noCarLayout.setVisibility(View.GONE);
			carImage.setBackgroundResource(GlobalVariable.currentUser.getCar().getImageId());
			myCarInfo.setText(GlobalVariable.currentUser.getCar().getDiscribText());
		}else{
			haveCarLayout.setVisibility(View.GONE);
			noCarLayout.setVisibility(View.VISIBLE);
			buyNewCar.setOnClickListener(this);
		}
        
        //初始化view3
        view3 = LayoutInflater.from(this).inflate(R.layout.auto_beauty_history_tab, null);
        beautyHistory = (ExpandableListView)view3.findViewById(R.id.auto_beauty_history);
        beautyHistory.setGroupIndicator(null);
        beautyHistory.setDivider(null);
        initBeautyHistory();
        beautyHistoryAdapter = new ReapirAndBeautyHistoryAdapter(this,beautyTitle, beautyContent, 
        		R.layout.history_title,R.layout.history_content_item);
        beautyHistory.setAdapter(beautyHistoryAdapter);
        
        //初始化view4
        view4 = LayoutInflater.from(this).inflate(R.layout.auto_repair_history_tab, null);
        repairHistory = (ExpandableListView)view4.findViewById(R.id.auto_repair_history);
        repairHistory.setGroupIndicator(null);
        repairHistory.setDivider(null);
        initRepairHistory();
        repairHistoryAdapter = new ReapirAndBeautyHistoryAdapter(this,repairTitle,repairContent,
        		R.layout.history_title,R.layout.history_content_item);
        repairHistory.setAdapter(repairHistoryAdapter);

		//初始化View5
		view5 = LayoutInflater.from(this).inflate(R.layout.my_orders_tab,null);
        
        //viewpager
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
		viewList.add(view5);
        
        //初始化各个标题
        titleList.add(getResources().getString(R.string.title_my_info));
        titleList.add(getResources().getString(R.string.title_my_car));
        titleList.add(getResources().getString(R.string.title_beauty_hitory));
        titleList.add(getResources().getString(R.string.title_repair_history));
		titleList.add(getResources().getString(R.string.titel_my_orders));
        adapter = new MyInfoPagerAdapter(viewList, titleList);
        pager.setPageTransformer(true, new CubeTransformer());
        pager.setAdapter(adapter);
       
        
	}
	private void inithanges(){
        imgAvatarImageView.setImageResource(GlobalVariable.currentUser.getAvatarImage());
        infoItems.clear();
		initItemList();
		infoItemAdapter.notifyDataSetChanged();
	}
	@Override
	protected void onResume() {
		super.onResume();
		inithanges();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.buy_a_new_car:
				CarModelsListActivity.startAction(MyInfoActivity.this);
				break;
		}
	}

	private void initItemList(){
		infoItems.add(new InfoItem(getResources().getString(R.string.info_item_user_name), GlobalVariable.currentUser.getUserName()));
		infoItems.add(new InfoItem(getResources().getString(R.string.info_item_real_name),GlobalVariable.currentUser.getRealName()));
		infoItems.add(new InfoItem(getResources().getString(R.string.info_item_sex),GlobalVariable.currentUser.getSex()));
		infoItems.add(new InfoItem(getResources().getString(R.string.info_item_emial_address),GlobalVariable.currentUser.getEmail()));
		infoItems.add(new InfoItem(getResources().getString(R.string.info_item_tel),GlobalVariable.currentUser.getTelNumber()));
		infoItems.add(new InfoItem(getResources().getString(R.string.info_item_address),GlobalVariable.currentUser.getAddress()));
		infoItems.add(new InfoItem(getResources().getString(R.string.info_item_car_name),GlobalVariable.currentUser.getCar()==null?
				getResources().getString(R.string.info_item_no_car):GlobalVariable.currentUser.getCar().getName()));
	}
	private void initRepairHistory(){
		repairTitle = new ArrayList<String>();
		repairTitle.add("2012-05-06");
		repairTitle.add("2012-06-06");
		repairTitle.add("2015-04-06");
		repairTitle.add("2016-05-06");
		
		repairContent = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("ά��Ա������");
		list1.add("���ã�20��");
		list1.add("ά�����ݣ��ײ�1");
		list1.add("ά�޵꣺ά�޵�1");
		repairContent.put("2012-05-06", list1);
		list1 = new ArrayList<String>();
		list1.add("ά��Ա������");
		list1.add("���ã�20��");
		list1.add("ά�����ݣ��ײ�1");
		list1.add("ά�޵꣺ά�޵�1");
		repairContent.put("2012-06-06", list1);
		list1 = new ArrayList<String>();
		list1.add("ά��Ա������");
		list1.add("���ã�20��");
		list1.add("ά�����ݣ��ײ�1");
		list1.add("ά�޵꣺ά�޵�1");
		repairContent.put("2015-04-06", list1);
		list1 = new ArrayList<String>();
		list1.add("ά��Ա������");
		list1.add("���ã�20��");
		list1.add("ά�����ݣ��ײ�1");
		list1.add("ά�޵꣺ά�޵�1");
		repairContent.put("2016-05-06", list1);
		
	}
	private void initBeautyHistory(){
		beautyTitle = new ArrayList<String>();
		beautyTitle.add("2012-05-06");
		beautyTitle.add("2012-06-06");
		beautyTitle.add("2015-04-06");
		beautyTitle.add("2016-05-06");
		beautyContent = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("����Ա������");
		list1.add("���ã�20��");
		list1.add("�������ݣ��ײ�1");
		list1.add("���ݵ꣺���ݵ�1");
		beautyContent.put("2012-05-06", list1);
		list1 = new ArrayList<String>();
		list1.add("����Ա������");
		list1.add("���ã�20��");
		list1.add("�������ݣ��ײ�1");
		list1.add("���ݵ꣺���ݵ�1");
		beautyContent.put("2012-06-06", list1);
		list1 = new ArrayList<String>();
		list1.add("����Ա������");
		list1.add("���ã�20��");
		list1.add("�������ݣ��ײ�1");
		list1.add("���ݵ꣺���ݵ�1");
		beautyContent.put("2015-04-06", list1);
		list1 = new ArrayList<String>();
		list1.add("����Ա������");
		list1.add("���ã�20��");
		list1.add("�������ݣ��ײ�1");
		list1.add("���ݵ꣺���ݵ�1");
		beautyContent.put("2016-05-06", list1);
		
	}
	
	
}
