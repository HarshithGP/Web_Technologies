package com.harshith.hw9.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LegislatorsResponseModel {
	@SerializedName("results")
	@Expose
	public List<Legislator> result;

	@SerializedName("count")
	@Expose
	public Integer count;

	@SerializedName("page")
	@Expose
	public Page page;

	public List<Legislator> getResult() {
		return result;
	}

	public Integer getCount() {
		return count;
	}

	public Page getPage() {
		return page;
	}
}
