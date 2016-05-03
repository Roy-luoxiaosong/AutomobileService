package com.roy.automobileservice.cls;

public class HeadSculpture {
	private int imageId;
	private String name;
	public HeadSculpture(int imageId, String name) {
		super();
		this.imageId = imageId;
		this.name = name;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
