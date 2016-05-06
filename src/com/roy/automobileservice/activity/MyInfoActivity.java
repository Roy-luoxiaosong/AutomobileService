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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.InfoItemAdapter;
import com.roy.automobileservice.adapter.MyInfoPagerAdapter;
import com.roy.automobileservice.adapter.ReapirAndBeautyHistoryAdapter;
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
	//汽车美容历史和维修历史
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
	private View view1,view2,view3,view4;
	private Button modifyInfo;
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
      	
      	//对view1的内容初始化
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
        
        //对view2的内容做初始化
        view2 = LayoutInflater.from(this).inflate(R.layout.my_car_info_tab, null);
        
        //对view3的内容做初始化
        view3 = LayoutInflater.from(this).inflate(R.layout.auto_beauty_history_tab, null);
        beautyHistory = (ExpandableListView)view3.findViewById(R.id.auto_beauty_history);
        beautyHistory.setGroupIndicator(null);
        beautyHistory.setDivider(null);
        initBeautyHistory();
        beautyHistoryAdapter = new ReapirAndBeautyHistoryAdapter(this,beautyTitle, beautyContent, 
        		R.layout.history_title,R.layout.history_content_item);
        beautyHistory.setAdapter(beautyHistoryAdapter);
        
        //对view4的内容做初始化
        view4 = LayoutInflater.from(this).inflate(R.layout.auto_repair_history_tab, null);
        repairHistory = (ExpandableListView)view4.findViewById(R.id.auto_repair_history);
        repairHistory.setGroupIndicator(null);
        repairHistory.setDivider(null);
        initRepairHistory();
        repairHistoryAdapter = new ReapirAndBeautyHistoryAdapter(this,repairTitle,repairContent,
        		R.layout.history_title,R.layout.history_content_item);
        repairHistory.setAdapter(repairHistoryAdapter);
        
        //viewpager开始添加view
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        
        //页签项
        titleList.add(getResources().getString(R.string.title_my_info));
        titleList.add(getResources().getString(R.string.title_my_car));
        titleList.add(getResources().getString(R.string.title_beauty_hitory));
        titleList.add(getResources().getString(R.string.title_repair_history));
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
		list1.add("维修员：张三");
		list1.add("费用：20块");
		list1.add("维修内容：套餐1");
		list1.add("维修店：维修店1");
		repairContent.put("2012-05-06", list1);
		list1 = new ArrayList<String>();
		list1.add("维修员：张三");
		list1.add("费用：20块");
		list1.add("维修内容：套餐1");
		list1.add("维修店：维修店1");
		repairContent.put("2012-06-06", list1);
		list1 = new ArrayList<String>();
		list1.add("维修员：张三");
		list1.add("费用：20块");
		list1.add("维修内容：套餐1");
		list1.add("维修店：维修店1");
		repairContent.put("2015-04-06", list1);
		list1 = new ArrayList<String>();
		list1.add("维修员：张三");
		list1.add("费用：20块");
		list1.add("维修内容：套餐1");
		list1.add("维修店：维修店1");
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
		list1.add("美容员：张三");
		list1.add("费用：20块");
		list1.add("美容内容：套餐1");
		list1.add("美容店：美容店1");
		beautyContent.put("2012-05-06", list1);
		list1 = new ArrayList<String>();
		list1.add("美容员：张三");
		list1.add("费用：20块");
		list1.add("美容内容：套餐1");
		list1.add("美容店：美容店1");
		beautyContent.put("2012-06-06", list1);
		list1 = new ArrayList<String>();
		list1.add("美容员：张三");
		list1.add("费用：20块");
		list1.add("美容内容：套餐1");
		list1.add("美容店：美容店1");
		beautyContent.put("2015-04-06", list1);
		list1 = new ArrayList<String>();
		list1.add("美容员：张三");
		list1.add("费用：20块");
		list1.add("美容内容：套餐1");
		list1.add("美容店：美容店1");
		beautyContent.put("2016-05-06", list1);
		
	}
	
	
}
