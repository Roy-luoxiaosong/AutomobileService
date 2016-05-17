package com.roy.automobileservice.cls;

/**
 * Created by Roy on 2016/5/10.
 */
public class CarOrder {
    private String carName;
    private String carDetialName;
    private String userName;
    private String date;
    private OrderState state;

    public CarOrder() {
    }
    public static enum OrderState{
        SUBMMIT,CHULIZHONG,WANCHENG
    }
    public CarOrder(String carName, String carDetialName, String userName,String date) {
        this.carName = carName;
        this.carDetialName = carDetialName;
        this.userName = userName;
        this.date = date;
        this.state = OrderState.SUBMMIT;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }
}
