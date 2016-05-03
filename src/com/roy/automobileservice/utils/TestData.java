package com.roy.automobileservice.utils;

import java.util.ArrayList;
import java.util.List;

import com.roy.automobileservice.R;
import com.roy.automobileservice.activity.CarModelsListActivity;
import com.roy.automobileservice.cls.HeadSculpture;
import com.roy.automobileservice.cls.User;

public class TestData {
	public static List<User> userTestList = new ArrayList<User>();
	public static List<HeadSculpture> headSculpturesList = new ArrayList<HeadSculpture>();
	public static void initUserTest(){
		User userNameItem = new User();
        userNameItem.setUserName("幼儿园大哥");
        userNameItem.setAvatarImage(R.drawable.user_default_icon1);
        userNameItem.setCar(CarModelsListActivity.carList.get(0));
        userNameItem.setEmail("user1@qq.com");
        userNameItem.setPassword("user1");
        userNameItem.setRealName("叶良辰");
        userNameItem.setSex("男");
        userNameItem.setTelNumber("15062356840");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName("厕所扛把子");
        userNameItem.setAvatarImage(R.drawable.user_default_icon2);
        userNameItem.setCar(CarModelsListActivity.carList.get(2));
        userNameItem.setEmail("user2@qq.com");
        userNameItem.setPassword("user2");
        userNameItem.setRealName("赵日天");
        userNameItem.setSex("男");
        userNameItem.setTelNumber("18665987586");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName("勾魂使者");
        userNameItem.setAvatarImage(R.drawable.user_default_icon3);
        userNameItem.setCar(null);
        userNameItem.setEmail("user3@qq.com");
        userNameItem.setPassword("user3");
        userNameItem.setRealName("郭美美");
        userNameItem.setSex("女");
        userNameItem.setTelNumber("13556789564");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName("少女杀手");
        userNameItem.setAvatarImage(R.drawable.user_default_icon4);
        userNameItem.setCar(CarModelsListActivity.carList.get(6));
        userNameItem.setEmail("user4@qq.com");
        userNameItem.setPassword("user4");
        userNameItem.setRealName("犀利哥");
        userNameItem.setSex("男");
        userNameItem.setTelNumber("14156942536");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName("宅男女神");
        userNameItem.setAvatarImage(R.drawable.user_default_icon5);
        userNameItem.setCar(CarModelsListActivity.carList.get(10));
        userNameItem.setEmail("user5@qq.com");
        userNameItem.setPassword("user5");
        userNameItem.setRealName("papi酱");
        userNameItem.setSex("女");
        userNameItem.setTelNumber("16253462358");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName("逗比先生");
        userNameItem.setAvatarImage(R.drawable.user_default_icon6);
        userNameItem.setCar(CarModelsListActivity.carList.get(5));
        userNameItem.setEmail("user6@qq.com");
        userNameItem.setPassword("user6");
        userNameItem.setRealName("王尼玛");
        userNameItem.setSex("男");
        userNameItem.setTelNumber("14123569871");
        userTestList.add(userNameItem);
        
        HeadSculpture temSculpture = new HeadSculpture(R.drawable.user_default_icon1, "默认头像1");
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon2, "默认头像2");
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon3, "默认头像3");
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon4, "默认头像4");
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon5, "默认头像5");
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon6, "默认头像6");
        headSculpturesList.add(temSculpture);
	}
	
}
