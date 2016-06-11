package com.roy.automobileservice.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.layout.ViewPagerIndicator;
import com.roy.automobileservice.layout.VpSimpleFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Roy on 2016/6/9.
 */
public class ManagerActivity extends FragmentActivity {
    public static void startAction(Context context) {
        Intent intent = new Intent(context, ManagerActivity.class);
        context.startActivity(intent);
    }
    private ViewPager mViewPager;
    private ViewPagerIndicator mViewPagerIndicator;

    private List<String> mTitles = Arrays.asList("订单管理","员工管理","用户管理");
    private List<VpSimpleFragment> mContents = new ArrayList<>();

    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.manager_login_activity);
        initViews();
        initDatas();
        //设置标题
        TextView textView = (TextView)findViewById(R.id.title_back_text);
        textView.setText(" ");
        mViewPagerIndicator.setVisibleTabCount(3);
        mViewPagerIndicator.setTabItemTitles(mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPagerIndicator.setViewPager(mViewPager,0);




    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_manager_viewpager);
        mViewPagerIndicator = (ViewPagerIndicator)findViewById(R.id.id_indicator);



    }
    private void initDatas(){

        VpSimpleFragment fragment = VpSimpleFragment.newInstance(R.layout.manager_order_view);

        mContents.add(fragment);
        fragment = VpSimpleFragment.newInstance(R.layout.manager_staff_view);

        mContents.add(fragment);
        fragment = VpSimpleFragment.newInstance(R.layout.manager_user_view);
        mContents.add(fragment);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
               return mContents.get(i);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };

    }
}
