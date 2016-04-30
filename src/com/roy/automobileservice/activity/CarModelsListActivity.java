package com.roy.automobileservice.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.CarAdapter;
import com.roy.automobileservice.cls.Car;
import com.roy.automobileservice.layout.TitleBackLayout;

public class CarModelsListActivity extends Activity{
	private List<Car> carList = new ArrayList<Car>();
	
	public static void startAction(Context context){
		Intent intent = new Intent(context,CarModelsListActivity.class);
		context.startActivity(intent);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.car_models_list_view);
		getCars();//初始化汽车数据
		//设置标题
		TitleBackLayout title  = (TitleBackLayout)findViewById(R.id.car_models_list_title);
		TextView textView = (TextView)title.findViewById(R.id.title_back_text);
		textView.setText(R.string.all_car_models);
		
		CarAdapter carAdapter = new CarAdapter(CarModelsListActivity.this, R.layout.car_item, carList);
		ListView listView = (ListView)findViewById(R.id.car_models_listview);
		listView.setAdapter(carAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				Car car = carList.get(position);
				CarInfoActivity.actionStart(CarModelsListActivity.this, car.getName(),
						car.getImageId(), car.getDiscribText());
			}
		});
	}
	private void getCars(){
		for(int i=1;i<20;i++){
			Car car = new Car("第"+i+"辆车",R.drawable.b_auto_beauty,
					"这是第"+i+"ninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasjninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasninfnifajfdlasflajfkdjsakfjadslfjsalfjdslajfkasflajfkldsajfakfjasljfdasklfjalkfjalkfdjkasfjalfjak辆车");
			carList.add(car);
		}
	}
	
	
}
