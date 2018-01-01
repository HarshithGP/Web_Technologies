package com.harshith.hw9.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CommitteeResposeModel {

	@SerializedName("results")
	@Expose
	public List<Committee> results;

	@SerializedName("count")
	@Expose
	public Integer count;

	@SerializedName("page")
	@Expose
	public Page page;


	public List<Committee> getResults() {
		return results;
	}

	public Integer getCount() {
		return count;
	}

	public Page getPage() {
		return page;
	}
}
