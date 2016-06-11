package com.roy.automobileservice.cls;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {
	private String userName;
    private int avatarImage;
    private String telNumber;
    private String email;
    private String password;
    private String sex;
    private String realName;
	private ChinaCar car;
	private String carName;
    private String address;
    public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public ChinaCar getCar() {
		return car;
	}
	public void setCar(ChinaCar car) {
		this.car = car;
	}
	public String getCarName(){
		return carName;
	}
	public void setCarName(String carName){
		this.carName = carName;
	}
	public User(){}
    public User(String userName, int avatarImage) {
		super();
		this.userName = userName;
		this.avatarImage = avatarImage;
	}

	public int getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(int avatarImage) {
        this.avatarImage = avatarImage;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
