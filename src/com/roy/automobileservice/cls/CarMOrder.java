package com.roy.automobileservice.cls;

import com.roy.automobileservice.utils.OrderHP;

import cn.bmob.v3.BmobObject;

/**
 * Created by Roy on 2016/6/10.
 */
public class CarMOrder extends BmobObject {
    private String userName;
    private String mileage;
    private String state = OrderHP.ORDER_SUBMIT_STATE;
    private String staffName;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public CarMOrder() {

    }

    public CarMOrder(String userName, String mileage) {
        this.userName = userName;
        this.mileage = mileage;
    }
}
