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
        userNameItem.setUserName("�׶�԰���");
        userNameItem.setAvatarImage(R.drawable.user_default_icon1);
        userNameItem.setCar(CarModelsListActivity.carList.get(0));
        userNameItem.setEmail("user1@qq.com");
        userNameItem.setPassword("user1");
        userNameItem.setRealName("Ҷ����");
        userNameItem.setSex("��");
        userNameItem.setTelNumber("15062356840");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName("����������");
        userNameItem.setAvatarImage(R.drawable.user_default_icon2);
        userNameItem.setCar(CarModelsListActivity.carList.get(2));
        userNameItem.setEmail("user2@qq.com");
        userNameItem.setPassword("user2");
        userNameItem.setRealName("������");
        userNameItem.setSex("��");
        userNameItem.setTelNumber("18665987586");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName("����ʹ��");
        userNameItem.setAvatarImage(R.drawable.user_default_icon3);
        userNameItem.setCar(null);
        userNameItem.setEmail("user3@qq.com");
        userNameItem.setPassword("user3");
        userNameItem.setRealName("������");
        userNameItem.setSex("Ů");
        userNameItem.setTelNumber("13556789564");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName("��Ůɱ��");
        userNameItem.setAvatarImage(R.drawable.user_default_icon4);
        userNameItem.setCar(CarModelsListActivity.carList.get(6));
        userNameItem.setEmail("user4@qq.com");
        userNameItem.setPassword("user4");
        userNameItem.setRealName("Ϭ����");
        userNameItem.setSex("��");
        userNameItem.setTelNumber("14156942536");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName("լ��Ů��");
        userNameItem.setAvatarImage(R.drawable.user_default_icon5);
        userNameItem.setCar(CarModelsListActivity.carList.get(10));
        userNameItem.setEmail("user5@qq.com");
        userNameItem.setPassword("user5");
        userNameItem.setRealName("papi��");
        userNameItem.setSex("Ů");
        userNameItem.setTelNumber("16253462358");
        userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName("��������");
        userNameItem.setAvatarImage(R.drawable.user_default_icon6);
        userNameItem.setCar(CarModelsListActivity.carList.get(5));
        userNameItem.setEmail("user6@qq.com");
        userNameItem.setPassword("user6");
        userNameItem.setRealName("������");
        userNameItem.setSex("��");
        userNameItem.setTelNumber("14123569871");
        userTestList.add(userNameItem);
        
        HeadSculpture temSculpture = new HeadSculpture(R.drawable.user_default_icon1, "Ĭ��ͷ��1");
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon2, "Ĭ��ͷ��2");
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon3, "Ĭ��ͷ��3");
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon4, "Ĭ��ͷ��4");
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon5, "Ĭ��ͷ��5");
        headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon6, "Ĭ��ͷ��6");
        headSculpturesList.add(temSculpture);
	}
	
}
