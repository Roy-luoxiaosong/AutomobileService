package com.roy.automobileservice.cls;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

/**
 * Created by Roy on 2016/5/12.
 */
public class ChinaCar extends BmobObject{
    private String carName;
    private String config;
    private Integer heat;
    private List<String> imageIds;
    private String price;

    public ChinaCar(){}
    public ChinaCar(String carName, String config, Integer heat, List<String> imageIds, String price) {
        this.carName = carName;
        this.config = config;
        this.heat = heat;
        this.imageIds = imageIds;
        this.price = price;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Integer getHeat() {
        return heat;
    }

    public void setHeat(Integer heat) {
        this.heat = heat;
    }

    public List<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<String> imageIds) {
        this.imageIds = imageIds;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
