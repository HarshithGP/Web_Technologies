package com.harshith.hw9.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LastVersion implements Parcelable {

	@SerializedName("version_code")
	@Expose
	public String versionCode;
	@SerializedName("issued_on")
	@Expose
	public String issuedOn;
	@SerializedName("version_name")
	@Expose
	public String versionName;
	@SerializedName("bill_version_id")
	@Expose
	public String billVersionId;
	@SerializedName("urls")
	@Expose
	public FileUrls urls;
	@SerializedName("pages")
	@Expose
	public Integer pages;

	public String getVersionCode() {
		return versionCode;
	}

	public String getIssuedOn() {
		return issuedOn;
	}

	public String getVersionName() {
		if(versionName!=null)
			return versionName;
		else
			return "N/A";
	}

	public String getBillVersionId() {
		return billVersionId;
	}

	public FileUrls getUrls() {
		return urls;
	}

	public Integer getPages() {
		return pages;
	}

	protected LastVersion(Parcel in) {
		versionCode = in.readString();
		issuedOn = in.readString();
		versionName = in.readString();
		billVersionId = in.readString();
		urls = (FileUrls) in.readValue(FileUrls.class.getClassLoader());
		pages = in.readByte() == 0x00 ? null : in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(versionCode);
		dest.writeString(issuedOn);
		dest.writeString(versionName);
		dest.writeString(billVersionId);
		dest.writeValue(urls);
		if (pages == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeInt(pages);
		}
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<LastVersion> CREATOR = new Parcelable.Creator<LastVersion>() {
		@Override
		public LastVersion createFromParcel(Parcel in) {
			return new LastVersion(in);
		}

		@Override
		public LastVersion[] newArray(int size) {
			return new LastVersion[size];
		}
	};
}