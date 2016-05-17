package com.roy.automobileservice.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import com.roy.automobileservice.adapter.ViewPagerAdapter;

public class ViewPagerCycleFragment extends Fragment implements OnPageChangeListener{

	private View view;
	private ViewPager mViewPaper;
	private List<ImageView> images;
	private List<View> dots;
	private int currentItem;
	//��¼��һ�ε��λ��
	private int oldPosition = 0;
	//���ͼƬ��id
	private int[] imageIds;
	//���ͼƬ�ı���
	private String[]  titles;
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
		
		mViewPaper.setOnPageChangeListener(this);
		return view;
	}
	private void init(){
		 imageIds = new int[]{
				 R.drawable.h220,
				 R.drawable.h230,
				 R.drawable.h330,
				 R.drawable.h530,
				 R.drawable.v3,
				 R.drawable.v5,
		};
	  titles = new String[]{
			  	"中华H220",
			  	"中华H230",
			  	"中华H330",
			  	"中华H5300",
			  	"中华V3",
			  	"中华V5",
	  };
	  mViewPaper = (ViewPager) view.findViewById(R.id.vp);
		
		images = new ArrayList<>();
		for(int i = 0; i < imageIds.length; i++){
			ImageView imageView = new ImageView(getActivity());
			imageView.setBackgroundResource(imageIds[i]);
			images.add(imageView);
		}
		dots = new ArrayList<>();
		dots.add(view.findViewById(R.id.dot_0));
		dots.add(view.findViewById(R.id.dot_1));
		dots.add(view.findViewById(R.id.dot_2));
		dots.add(view.findViewById(R.id.dot_3));
		dots.add(view.findViewById(R.id.dot_4));
		dots.add(view.findViewById(R.id.dot_5));


		
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
		}
	};
	@Override
	public void onPageSelected(int position) {
		title.setText(titles[position]);
		dots.get(position).setBackgroundResource(R.drawable.dot_focused);
		dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
		//CarTemp car = CarModelsListActivity.carList.get(1);
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
