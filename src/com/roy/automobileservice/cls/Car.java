package com.roy.automobileservice.cls;

public class Car {
	private String name;
	private int imageId;
	private String discribText;

	public Car(){}
	public Car(String name,int imageId,String discribText) {
		super();
		this.name = name;
		this.imageId = imageId;
		this.discribText = discribText;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getDiscribText() {
		return discribText;
	}
	public void setDiscribText(String discribText) {
		this.discribText = discribText;
	}
	
}
