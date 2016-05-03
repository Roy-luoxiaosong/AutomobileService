package com.roy.automobileservice.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MyInfoPagerAdapter extends PagerAdapter implements OnPageChangeListener{
	private ArrayList<View> viewList;
	private ArrayList<String> titleList;
	
	public MyInfoPagerAdapter(ArrayList<View> viewList,
			ArrayList<String> titleList) {
		super();
		this.viewList = viewList;
		this.titleList = titleList;
	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
    public void destroyItem(ViewGroup container, int position,
            Object object) {
        ((ViewPager) container).removeView(viewList.get(position));
    }
	@Override
    public Object instantiateItem(ViewGroup container, int position) {
    	((ViewPager) container).addView(viewList.get(position));
    	Log.d("luoxiaosong", " "+position);
        return viewList.get(position);
    }
	@Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
