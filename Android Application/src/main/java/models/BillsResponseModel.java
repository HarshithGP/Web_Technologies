package com.harshith.hw9.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class BillsResponseModel {
	@SerializedName("results")
	@Expose
	public List<Bill> results=new ArrayList<>();

	@SerializedName("count")
	@Expose
	public Integer count;

	@SerializedName("page")
	@Expose
	public Page page;

	public List<Bill> getResults() {
		return results;
	}

	public Integer getCount() {
		return count;
	}

	public Page getPage() {
		return page;
	}
}
