package com.roy.automobileservice.activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;

import com.roy.automobileservice.adapter.CarTempAdapter;


import com.roy.automobileservice.cls.ChinaCar;


import java.util.ArrayList;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

import cn.bmob.v3.listener.UpdateListener;

public class CarModelsListActivity extends BaseActivity{
	

	public static void startAction(Context context){
		Intent intent = new Intent(context,CarModelsListActivity.class);
		context.startActivity(intent);
		
	}

	private CarTempAdapter carAdapter;

	public static List<ChinaCar> cars = new ArrayList<>();
	private LinearLayout carLinearLayout;

	private ProgressBar progressBar;

	@Override
	protected void onDestroy() {
		super.onDestroy();


	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.car_models_list_view);

		TextView textView = (TextView)findViewById(R.id.title_back_text);
		carLinearLayout = (LinearLayout)findViewById(R.id.car_models);
		progressBar = (ProgressBar)findViewById(R.id.car_progressBar);
		textView.setText(R.string.all_car_models);
		//loadData = (ProgressBar)findViewById(R.id.load_data);

		initData();

	}
	private void initData(){
		String bql ="select * from ChinaCar";
		new BmobQuery<ChinaCar>().doSQLQuery(this, bql, new SQLQueryListener<ChinaCar>() {
			@Override
			public void done(BmobQueryResult<ChinaCar> bmobQueryResult, BmobException e) {

				if (e==null) {
					cars =  bmobQueryResult.getResults();

					carAdapter = new CarTempAdapter(CarModelsListActivity.this, R.layout.car_item_temp,cars);
					carAdapter.notifyDataSetChanged();
					ListView listView = (ListView)findViewById(R.id.car_models_listview);
					listView.setAdapter(carAdapter);
					carLinearLayout.setVisibility(View.VISIBLE);
					progressBar.setVisibility(View.GONE);
					listView.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                long id) {
                            ChinaCar car = CarModelsListActivity.cars.get(position);
                            ChinaCar chinaCar = new ChinaCar();
                            chinaCar.setHeat(car.getHeat()+1);
                            chinaCar.update(CarModelsListActivity.this, car.getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    //Log.d("tag","更新成功 "+"succeed");
                                }

                                @Override
                                public void onFailure(int i, String s) {

                                }
                            });
                            CarInfoActivity.actionStart(CarModelsListActivity.this, car.getCarName(),
									(ArrayList<String>) car.getImageIds(), car.getConfig());

                        }
                    });
				}else {
					Toast.makeText(CarModelsListActivity.this,"加载数据失败，请检查网络设置",Toast.LENGTH_SHORT).show();
				}


			}


		});



	}
	
	
}
