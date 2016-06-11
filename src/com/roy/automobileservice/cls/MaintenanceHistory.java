package com.roy.automobileservice.cls;

import cn.bmob.v3.BmobObject;

/**
 * Created by Roy on 2016/6/11.
 */
public class MaintenanceHistory extends BmobObject {
    private String userName;
    private String staffName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public MaintenanceHistory() {
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public MaintenanceHistory(String userName, String staffName) {
        this.userName = userName;
        this.staffName = staffName;
    }
}
