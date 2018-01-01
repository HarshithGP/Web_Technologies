package com.harshith.hw9.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Page {
	@SerializedName("count")
	@Expose
	public Integer count;
	@SerializedName("per_page")
	@Expose
	public Integer perPage;
	@SerializedName("page")
	@Expose
	public Integer page;
}
