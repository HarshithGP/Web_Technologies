package com.harshith.hw9.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FileUrls implements Parcelable {

	@SerializedName("html")
	@Expose
	public String html;
	@SerializedName("pdf")
	@Expose
	public String pdf;
	@SerializedName("xml")
	@Expose
	public String xml;

	public String getHtml() {
		return html;
	}

	public String getPdf() {
		if(pdf!=null)
			return pdf;
		else
			return "N/A";
	}

	public String getXml() {
		return xml;
	}

	protected FileUrls(Parcel in) {
		html = in.readString();
		pdf = in.readString();
		xml = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(html);
		dest.writeString(pdf);
		dest.writeString(xml);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<FileUrls> CREATOR = new Parcelable.Creator<FileUrls>() {
		@Override
		public FileUrls createFromParcel(Parcel in) {
			return new FileUrls(in);
		}

		@Override
		public FileUrls[] newArray(int size) {
			return new FileUrls[size];
		}
	};
}
