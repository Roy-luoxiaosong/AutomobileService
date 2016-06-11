package com.roy.automobileservice.cls;

import cn.bmob.v3.BmobObject;

/**
 * Created by Roy on 2016/6/10.
 */
public class Staff extends BmobObject {
    private String name;
    private String tel;
    private String email;
    private Integer orderCount;
    private String password;
    private String depart;

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {

        this.depart = depart;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Staff() {
    }

    public Staff(String name, String tel, String email,String password) {
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }
}
