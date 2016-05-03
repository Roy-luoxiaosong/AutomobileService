package com.roy.automobileservice.cls;

public class Car {
	private String name;
	private int imageId;
	private String discribText;
	private String price;
	private int heat;

	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getHeat() {
		return heat;
	}
	public void setHeat(int heat) {
		this.heat = heat;
	}
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
