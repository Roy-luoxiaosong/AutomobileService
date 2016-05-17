package com.roy.automobileservice.cls;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Roy on 2016/5/12.
 */
public class CarMaintenance extends BmobObject {
    private String mileage;
    private List<String> baseContents;
    private List<String> recommendContent;

    public CarMaintenance(){}

    public CarMaintenance(String mileage, List<String> baseContents, List<String> recommendContent) {
        this.mileage = mileage;
        this.baseContents = baseContents;
        this.recommendContent = recommendContent;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public List<String> getBaseContents() {
        return baseContents;
    }

    public void setBaseContents(List<String> baseContents) {
        this.baseContents = baseContents;
    }

    public List<String> getRecommendContent() {
        return recommendContent;
    }

    public void setRecommendContent(List<String> recommendContent) {
        this.recommendContent = recommendContent;
    }
}
