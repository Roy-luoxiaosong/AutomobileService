package com.roy.automobileservice.activity;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Roy on 2016/5/9.
 */
public class CarTemp extends BmobObject{
    private String carName;
    private String imageIds;
    private Integer heat;
    private String config;
    private String price;
    public CarTemp(){}
    public CarTemp(String carName, String imageIds, String price, Integer heat,
                   String config) {
        super();
        this.carName = carName;
        this.imageIds = imageIds;
        this.price = price;
        this.heat = heat;
        this.config = config;
    }
    public String getCarName() {
        return carName;
    }
    public void setCarName(String carName) {
        this.carName = carName;
    }
    public String getImageIds() {
        return imageIds;
    }
    public void setImageIds(String imageIds) {
        this.imageIds = imageIds;
    }

    public Integer getHeat() {
        return heat;
    }
    public void setHeat(Integer heat) {
        this.heat = heat;
    }
    public String getConfig() {
        return config;
    }
    public void setConfig(String config) {
        this.config = config;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}
