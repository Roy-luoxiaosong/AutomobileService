package com.roy.automobileservice.cls;

public class UserNameItem {
	private String userName;
    private int avatarImage;
    public UserNameItem(){}
    public UserNameItem(String userName, int avatarImage) {
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
