package com.sphericalelephant.zeitgeistng.data;

import com.google.gson.annotations.SerializedName;

public class Tag {
	private int id;
	private String tagName;
	@SerializedName("count")
	private int itemCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
}
