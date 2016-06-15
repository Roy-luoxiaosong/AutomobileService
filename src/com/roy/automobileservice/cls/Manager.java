package com.roy.automobileservice.cls;

import cn.bmob.v3.BmobObject;

/**
 * Created by Roy on 2016/6/14.
 */
public class Manager extends BmobObject{
    private String name;
    private String password;

    public Manager(){}
    public Manager(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
