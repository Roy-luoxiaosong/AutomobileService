package com.roy.automobileservice.cls;

import com.roy.automobileservice.utils.OrderHP;

import cn.bmob.v3.BmobObject;

/**
 * Created by Roy on 2016/5/10.
 */
public class CarOrder extends BmobObject {
    private String carName;
    private String carDetialName;
    private String userName;
    //private String date;
    private String state = OrderHP.ORDER_SUBMIT_STATE;
    private String staffName;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public CarOrder() {
    }

    public CarOrder(String carName, String carDetialName, String userName) {
        this.carName = carName;
        this.carDetialName = carDetialName;
        this.userName = userName;
        //this.date = date;
        this.state = OrderHP.ORDER_SUBMIT_STATE;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarDetialName() {
        return carDetialName;
    }

    public void setCarDetialName(String carDetialName) {
        this.carDetialName = carDetialName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*public String getDate() {
        return date;
    }*/

   /* public void setDate(String date) {
        this.date = date;
    }*/

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
