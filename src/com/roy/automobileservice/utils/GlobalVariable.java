package com.roy.automobileservice.utils;

import com.roy.automobileservice.cls.Staff;
import com.roy.automobileservice.cls.User;

public class GlobalVariable {
	public static User currentUser = new User();
	public static Staff currentStaff = new Staff();
	public static final int MANAGER_LOGIN = 1;
	public static final int USER_LOGIN = 2;
	public static final int STAFF_LOGIN = 3;
	public static final int LOGIN_FALSE = -1;
	public static int loginType = LOGIN_FALSE;

	public static final String CAR_SALE_DEPART = "销售部";
	public static final String CAR_MAINTENANCE_DEPART = "保养部";
	public static final String CAR_BEAUTY_DEPART = "美容部";
	public static final String CAR_PART_DEPART = "配件部";



}
