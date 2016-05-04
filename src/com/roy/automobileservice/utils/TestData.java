package com.roy.automobileservice.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.roy.automobileservice.R;
import com.roy.automobileservice.activity.CarModelsListActivity;
import com.roy.automobileservice.cls.HeadSculpture;
import com.roy.automobileservice.cls.User;

public class TestData {
	public static List<User> userTestList = new ArrayList<User>();
	public static List<HeadSculpture> headSculpturesList = new ArrayList<HeadSculpture>();
	public static void initUserTest(Context context){
		User userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user1_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon1);
        userNameItem.setCar(CarModelsListActivity.carList.get(0));
        userNameItem.setEmail("user1@qq.com");
        userNameItem.setPassword("user1");
        userNameItem.setRealName(context.getResources().getString(R.string.user1_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user1_sex));
        userNameItem.setTelNumber("15062356840");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user2_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon2);
        userNameItem.setCar(CarModelsListActivity.carList.get(2));
        userNameItem.setEmail("user2@qq.com");
        userNameItem.setPassword("user2");
        userNameItem.setRealName(context.getResources().getString(R.string.user2_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user2_sex));
        userNameItem.setTelNumber("18665987586");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user3_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon3);
        userNameItem.setCar(null);
        userNameItem.setEmail("user3@qq.com");
        userNameItem.setPassword("user3");
        userNameItem.setRealName(context.getResources().getString(R.string.user3_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user3_sex));
        userNameItem.setTelNumber("13556789564");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user4_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon4);
        userNameItem.setCar(CarModelsListActivity.carList.get(6));
        userNameItem.setEmail("user4@qq.com");
        userNameItem.setPassword("user4");
        userNameItem.setRealName(context.getResources().getString(R.string.user4_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user4_sex));
        userNameItem.setTelNumber("14156942536");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user5_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon5);
        userNameItem.setCar(CarModelsListActivity.carList.get(10));
        userNameItem.setEmail("user5@qq.com");
        userNameItem.setPassword("user5");
        userNameItem.setRealName(context.getResources().getString(R.string.user5_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user5_sex));
        userNameItem.setTelNumber("16253462358");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user6_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon6);
        userNameItem.setCar(CarModelsListActivity.carList.get(5));
        userNameItem.setEmail("user6@qq.com");
        userNameItem.setPassword("user6");
        userNameItem.setRealName(context.getResources().getString(R.string.user6_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user6_sex));
        userNameItem.setTelNumber("14123569871");
        userTestList.add(userNameItem);
        
        HeadSculpture temSculpture = new HeadSculpture(R.drawable.user_default_icon1,
        		context.getResources().getString(R.string.default_cion1_name));
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon2, 
        		context.getResources().getString(R.string.default_cion2_name));
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon3, 
        		context.getResources().getString(R.string.default_cion3_name));
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon4,
        		context.getResources().getString(R.string.default_cion4_name));
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon5, 
        		context.getResources().getString(R.string.default_cion5_name));
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon6, 
        		context.getResources().getString(R.string.default_cion6_name));
        headSculpturesList.add(temSculpture);
	}
	
}
