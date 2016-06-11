package com.roy.automobileservice.cls;

import com.roy.automobileservice.utils.OrderHP;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Roy on 2016/6/10.
 */
public class CarBOrder extends BmobObject {
    private String userName;
    private List<String> beautyList;
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

    public List<String> getBeautyList() {
        return beautyList;
    }

    public void setBeautyList(List<String> beautyList) {
        this.beautyList = beautyList;
    }

    public CarBOrder() {

    }

    public CarBOrder(List<String> beautyList, String userName) {

        this.beautyList = beautyList;
        this.userName = userName;
    }
}
