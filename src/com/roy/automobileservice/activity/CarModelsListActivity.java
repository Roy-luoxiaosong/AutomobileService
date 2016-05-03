package com.roy.automobileservice.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.CarAdapter;
import com.roy.automobileservice.cls.Car;

public class CarModelsListActivity extends BaseActivity{
	public static List<Car> carList = new ArrayList<Car>();
	//private View viewPagerCycleFragment;
	public static void startAction(Context context){
		Intent intent = new Intent(context,CarModelsListActivity.class);
		context.startActivity(intent);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.car_models_list_view);
		//…Ë÷√±ÍÃ‚
		TextView textView = (TextView)findViewById(R.id.title_back_text);
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
	
	
	
}
