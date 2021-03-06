package com.roy.automobileservice.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.InfoItemAdapter;
import com.roy.automobileservice.adapter.MyInfoPagerAdapter;
import com.roy.automobileservice.adapter.ReapirAndBeautyHistoryAdapter;
import com.roy.automobileservice.adapter.UserCarBOrderAdapter;
import com.roy.automobileservice.adapter.UserCarMOrderAdapter;
import com.roy.automobileservice.adapter.UserCarOrderAdapter;
import com.roy.automobileservice.cls.BeautyHistory;
import com.roy.automobileservice.cls.CarBOrder;
import com.roy.automobileservice.cls.CarMOrder;
import com.roy.automobileservice.cls.CarOrder;
import com.roy.automobileservice.cls.ChinaCar;
import com.roy.automobileservice.cls.CubeTransformer;
import com.roy.automobileservice.cls.InfoItem;
import com.roy.automobileservice.cls.MaintenanceHistory;
import com.roy.automobileservice.cls.Staff;
import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.utils.GlobalVariable;
import com.roy.automobileservice.utils.SqlHelper;
import com.roy.automobileservice.utils.Utils;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class MyInfoActivity extends BaseActivity implements OnClickListener{
	public static void startAction(Context context){
		Intent intent = new Intent(context,MyInfoActivity.class);
		context.startActivity(intent);
	}


	private ExpandableListView repairHistory;
	private ExpandableListView beautyHistory;
	private ReapirAndBeautyHistoryAdapter repairHistoryAdapter;
	private ReapirAndBeautyHistoryAdapter beautyHistoryAdapter;
	private List<String> repairTitle;
	private Map<String,List<String>> mainContent;
	private List<String> beautyTitle;
	private Map<String, List<String>> beautyContent;
	
	private List<InfoItem> infoItems = new ArrayList<InfoItem>();
	private AvatarImageView imgAvatarImageView;
	private ViewPager pager = null;
	private PagerTabStrip tabStrip = null;
	private ArrayList<View> viewList = new ArrayList<>();
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
	private TextView carName;
	private TextView carType;
	//view5
	private ListView mCarOrderListView,mCarBOrderListView,mCarMOrderListView;
	private List<CarOrder> mCarOrderList;
	private List<CarBOrder> mCarBOrderList;
	private List<CarMOrder> mCarMOrderList;
	private TextView mCarOrderTitle,mCarBOrderTitle,mCarMOrderTitle;


	//申请美容和保养
	private Button askForMBtn;
	private Button askForBBtn;
	private String bql;
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
        imgAvatarImageView.setImageResource(Utils.getUserIconByInt(GlobalVariable.currentUser.getAvatarImage()));
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
		carName = (TextView)view2.findViewById(R.id.user_car_name);
		carType = (TextView)view2.findViewById(R.id.user_car_type);
		myCarInfo = (TextView)view2.findViewById(R.id.my_car_discribe_info);
		noCarLayout = (LinearLayout)view2.findViewById(R.id.no_car_layout);
		haveCarLayout = (LinearLayout)view2.findViewById(R.id.have_car_layout);
		buyNewCar = (Button)view2.findViewById(R.id.buy_a_new_car);

		new BmobQuery<ChinaCar>().doSQLQuery(MyInfoActivity.this, SqlHelper.SEARCH_CAR_FROM_BMBO, new SQLQueryListener<ChinaCar>() {
			@Override
			public void done(BmobQueryResult<ChinaCar> bmobQueryResult, BmobException e) {
				if(e==null){
					List<ChinaCar> cars = bmobQueryResult.getResults();
					ChinaCar car = Utils.getCarByName(GlobalVariable.currentUser.getCarName(),cars);
					if(car!=null){
						haveCarLayout.setVisibility(View.VISIBLE);
						noCarLayout.setVisibility(View.GONE);
						Picasso.with(MyInfoActivity.this).load(car.getImageIds().get(1)).into(carImage);
						myCarInfo.setText(car.getConfig());
						carName.setText(car.getCarName());
						bql = "select * from CarOrder where userName="+"'"+GlobalVariable.currentUser.getUserName()+"'"+"and state='交易完成'";
						new BmobQuery<CarOrder>().doSQLQuery(MyInfoActivity.this, bql, new SQLQueryListener<CarOrder>() {
							@Override
							public void done(BmobQueryResult<CarOrder> bmobQueryResult, BmobException e) {
								if(e==null&&bmobQueryResult.getResults().size()>0){
									carType.setText(bmobQueryResult.getResults().get(0).getCarDetialName());
									buyCarTime.setText(bmobQueryResult.getResults().get(0).getUpdatedAt().toString());
								}else{
									carType.setText("1.5MT精英型");
									buyCarTime.setText("2015-4-1");
								}
							}
						});


					}else{
						haveCarLayout.setVisibility(View.GONE);
						noCarLayout.setVisibility(View.VISIBLE);
						buyNewCar.setOnClickListener(MyInfoActivity.this);
					}
				}

			}
		});


        
        //初始化view3
        view3 = LayoutInflater.from(this).inflate(R.layout.auto_beauty_history_tab, null);
        beautyHistory = (ExpandableListView)view3.findViewById(R.id.auto_beauty_history);
		askForBBtn = (Button)view3.findViewById(R.id.beauty_request);
		askForBBtn.setOnClickListener(this);
        beautyHistory.setGroupIndicator(null);
        beautyHistory.setDivider(null);
        initBeautyHistory();

        //初始化view4
        view4 = LayoutInflater.from(this).inflate(R.layout.auto_repair_history_tab, null);
        repairHistory = (ExpandableListView)view4.findViewById(R.id.auto_repair_history);
		askForMBtn = (Button) view4.findViewById(R.id.repair_request);
		askForMBtn.setOnClickListener(this);
		repairHistory.setGroupIndicator(null);
        repairHistory.setDivider(null);
        initMaintenanceHistory();


		//初始化View5

		view5 = LayoutInflater.from(this).inflate(R.layout.my_orders_tab,null);
		initManageOrderViews(view5);
		initOrderViewData();

        
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
        imgAvatarImageView.setImageResource(Utils.getUserIconByInt(GlobalVariable.currentUser.getAvatarImage()));
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
			case R.id.repair_request:
				CarRepairActivity.startAction(MyInfoActivity.this);
				break;
			case R.id.beauty_request:
				CarBeautyActivity.startAction(MyInfoActivity.this);
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
				getResources().getString(R.string.info_item_no_car):GlobalVariable.currentUser.getCar().getCarName()));
	}
	private void initMaintenanceHistory(){
		repairTitle = new ArrayList<>();
		bql = "select * from MaintenanceHistory where userName="+"'"+GlobalVariable.currentUser.getUserName()+"'";
		new BmobQuery<MaintenanceHistory>().doSQLQuery(MyInfoActivity.this, bql, new SQLQueryListener<MaintenanceHistory>() {
			@Override
			public void done(BmobQueryResult<MaintenanceHistory> bmobQueryResult, BmobException e) {
				if(e==null){
					List<MaintenanceHistory> mh = bmobQueryResult.getResults();
					mainContent = new HashMap<>();
					final List<String> list1 = new ArrayList<>();
					Iterator<MaintenanceHistory> it = mh.iterator();
					while (it.hasNext()){
						final MaintenanceHistory tem = it.next();
						repairTitle.add(tem.getUpdatedAt().toString());
						bql = "select * from Staff where name="+"'"+tem.getStaffName()+"'";
						new BmobQuery<Staff>().doSQLQuery(MyInfoActivity.this, bql, new SQLQueryListener<Staff>() {
							@Override
							public void done(BmobQueryResult<Staff> bmobQueryResult, BmobException e) {
								if(e==null&&bmobQueryResult.getResults().size()>0){
									Staff staff = bmobQueryResult.getResults().get(0);
									list1.clear();
									list1.add("处理人："+staff.getName());
									list1.add("电话："+staff.getTel());
									list1.add("邮箱："+staff.getEmail());
									mainContent.put(tem.getUpdatedAt().toString(), list1);


								}else {
									Toast.makeText(MyInfoActivity.this,"获取处理人信息失败",Toast.LENGTH_SHORT).show();
								}
							}
						});
					}

						repairHistoryAdapter = new ReapirAndBeautyHistoryAdapter(MyInfoActivity.this,repairTitle, mainContent,
                                R.layout.history_title,R.layout.history_content_item);
						repairHistory.setAdapter(repairHistoryAdapter);


				}else{
					Toast.makeText(MyInfoActivity.this,"获取保养记录失败",Toast.LENGTH_SHORT).show();
				}
			}
		});

		
	}
	private void initBeautyHistory(){
		beautyTitle = new ArrayList<>();
		bql = "select * from BeautyHistory where userName="+"'"+GlobalVariable.currentUser.getUserName()+"'";
		new BmobQuery<BeautyHistory>().doSQLQuery(MyInfoActivity.this, bql, new SQLQueryListener<BeautyHistory>() {
			@Override
			public void done(BmobQueryResult<BeautyHistory> bmobQueryResult, BmobException e) {
				if(e==null){
					List<BeautyHistory> bh = bmobQueryResult.getResults();
					beautyContent = new HashMap<>();
					final List<String> list1 = new ArrayList<>();
					Iterator<BeautyHistory> it = bh.iterator();
					while (it.hasNext()){
						final BeautyHistory tem = it.next();
						beautyTitle.add(tem.getUpdatedAt().toString());
						bql = "select * from Staff where name="+"'"+tem.getStaffName()+"'";
						new BmobQuery<Staff>().doSQLQuery(MyInfoActivity.this, bql, new SQLQueryListener<Staff>() {
							@Override
							public void done(BmobQueryResult<Staff> bmobQueryResult, BmobException e) {
								if(e==null&&bmobQueryResult.getResults().size()>0){
									Staff staff = bmobQueryResult.getResults().get(0);
									list1.clear();
									list1.add("处理人："+staff.getName());
									list1.add("电话："+staff.getTel());
									list1.add("邮箱："+staff.getEmail());
									beautyContent.put(tem.getUpdatedAt().toString(), list1);

								}else {
									Toast.makeText(MyInfoActivity.this,"获取处理人信息失败",Toast.LENGTH_SHORT).show();
								}
							}
						});
					}

						beautyHistoryAdapter = new ReapirAndBeautyHistoryAdapter(MyInfoActivity.this,beautyTitle, beautyContent,
                                R.layout.history_title,R.layout.history_content_item);
						beautyHistory.setAdapter(beautyHistoryAdapter);


				}else{
					Toast.makeText(MyInfoActivity.this,"获取美容记录失败记录失败",Toast.LENGTH_SHORT).show();
				}
			}
		});

		
	}
	private void initManageOrderViews(View view){
		mCarOrderListView = (ListView)view.findViewById(R.id.manager_car_order_list);

		mCarBOrderListView = (ListView)view.findViewById(R.id.manager_beauty_order_list);
		mCarMOrderListView = (ListView)view.findViewById(R.id.manager_maintenance_order_list);
		mCarOrderTitle = (TextView)view.findViewById(R.id.car_order_title);
		mCarBOrderTitle = (TextView)view.findViewById(R.id.car_beauty_order_title);
		mCarMOrderTitle = (TextView)view.findViewById(R.id.car_m_order_title);
	}
	private void initOrderViewData(){
		bql = "select * from CarOrder where userName="+"'"+GlobalVariable.currentUser.getUserName()+"'";
		new BmobQuery<CarOrder>().doSQLQuery(MyInfoActivity.this, bql, new SQLQueryListener<CarOrder>() {
			@Override
			public void done(BmobQueryResult<CarOrder> bmobQueryResult, BmobException e) {
				if(e==null){
					mCarOrderList = bmobQueryResult.getResults();
					if(mCarOrderList!=null&&mCarOrderList.size()>0){
						UserCarOrderAdapter adapter = new UserCarOrderAdapter(MyInfoActivity.this,R.layout.car_order_item,mCarOrderList);
						mCarOrderListView.setAdapter(adapter);
						Utils.setListViewHeightBasedOnChildren(mCarOrderListView);
						mCarOrderTitle.setVisibility(View.VISIBLE);
					}
				}else {
					Toast.makeText(MyInfoActivity.this,"获取订单失败",Toast.LENGTH_SHORT).show();
				}
			}
		});
		bql = "select * from CarBOrder where userName="+"'"+GlobalVariable.currentUser.getUserName()+"'";
		new BmobQuery<CarBOrder>().doSQLQuery(MyInfoActivity.this, bql, new SQLQueryListener<CarBOrder>() {
			@Override
			public void done(BmobQueryResult<CarBOrder> bmobQueryResult, BmobException e) {
				if(e==null){
					mCarBOrderList = bmobQueryResult.getResults();
					if(mCarBOrderList!=null&&mCarBOrderList.size()>0){
						UserCarBOrderAdapter adapter = new UserCarBOrderAdapter(MyInfoActivity.this,R.layout.car_beauty_order_item,mCarBOrderList);
						mCarBOrderListView.setAdapter(adapter);
						Utils.setListViewHeightBasedOnChildren(mCarBOrderListView);
						mCarBOrderTitle.setVisibility(View.VISIBLE);
					}
				}else {
					Toast.makeText(MyInfoActivity.this,"获取订单失败",Toast.LENGTH_SHORT).show();
				}
			}
		});
		bql = "select * from CarMOrder where userName="+"'"+GlobalVariable.currentUser.getUserName()+"'";
		new BmobQuery<CarMOrder>().doSQLQuery(MyInfoActivity.this, bql, new SQLQueryListener<CarMOrder>() {
			@Override
			public void done(BmobQueryResult<CarMOrder> bmobQueryResult, BmobException e) {
				if(e==null){
					mCarMOrderList = bmobQueryResult.getResults();
					if(mCarMOrderList!=null&&mCarMOrderList.size()>0){
						UserCarMOrderAdapter adapter = new UserCarMOrderAdapter(MyInfoActivity.this,R.layout.car_maintenance_order_item,mCarMOrderList);
						mCarMOrderListView.setAdapter(adapter);
						Utils.setListViewHeightBasedOnChildren(mCarMOrderListView);
						mCarMOrderTitle.setVisibility(View.VISIBLE);
					}
				}
				else {
					Toast.makeText(MyInfoActivity.this,"获取订单失败",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
