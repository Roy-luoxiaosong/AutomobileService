package com.roy.automobileservice.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.activity.CarInfoActivity;
import com.roy.automobileservice.activity.CarModelsListActivity;
import com.roy.automobileservice.adapter.ViewPagerAdapter;
import com.roy.automobileservice.cls.Car;

public class ViewPagerCycleFragment extends Fragment implements OnPageChangeListener{

	private View view;
	private ViewPager mViewPaper;
	private List<ImageView> images;
	private List<View> dots;
	private int currentItem;
	//记录上一次点的位置
	private int oldPosition = 0;
	//存放图片的id
	private int[] imageIds;
	//存放图片的标题
	private int[]  titles;
	private TextView title;
	private ViewPagerAdapter adapter;
	private ScheduledExecutorService scheduledExecutorService;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.cycle_picture, null);
		init();
		
		adapter = new ViewPagerAdapter(images);
		mViewPaper.setAdapter(adapter);
		
		mViewPaper.setOnPageChangeListener((OnPageChangeListener) this); 
		return view;
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
	  titles = new int[]{
			  	R.string.car_appearance,	
	        	R.string.auto_beauty_option_text,	
	        	R.string.auto_repair_option_text,	
	        	R.string.auto_part_option_text,	
	        	R.string.road_assis_option_text
	        };
	  mViewPaper = (ViewPager) view.findViewById(R.id.vp);
		
		//显示的图片
		images = new ArrayList<ImageView>();
		for(int i = 0; i < imageIds.length; i++){
			ImageView imageView = new ImageView(getActivity());
			imageView.setBackgroundResource(imageIds[i]);
			images.add(imageView);
		}
		//显示的小点
		dots = new ArrayList<View>();
		dots.add(view.findViewById(R.id.dot_0));
		dots.add(view.findViewById(R.id.dot_1));
		dots.add(view.findViewById(R.id.dot_2));
		dots.add(view.findViewById(R.id.dot_3));
		dots.add(view.findViewById(R.id.dot_4));
		
		title = (TextView) view.findViewById(R.id.title);
		title.setText(titles[0]);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleWithFixedDelay(
				new ViewPageTask(), 
				2, 
				2, 
				TimeUnit.SECONDS);
	}
	private class ViewPageTask implements Runnable{

		@Override
		public void run() {
			currentItem = (currentItem + 1) % imageIds.length;
			mHandler.sendEmptyMessage(0);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			mViewPaper.setCurrentItem(currentItem);
		};
	};
	@Override
	public void onPageSelected(int position) {
		title.setText(titles[position]);
		dots.get(position).setBackgroundResource(R.drawable.dot_focused);
		dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
		//Car car = CarModelsListActivity.carList.get(1);
		//CarInfoActivity.actionStart(getActivity(), car.getName(), car.getImageId(), car.getDiscribText());
		oldPosition = position;
		currentItem = position;
		
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}
		

}
