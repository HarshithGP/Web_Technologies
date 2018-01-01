package com.harshith.hw9.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls implements Parcelable {

	@SerializedName("congress")
	@Expose
	public String congress;
	@SerializedName("govtrack")
	@Expose
	public String govtrack;
	@SerializedName("opencongress")
	@Expose
	public String opencongress;

	public String getCongress() {
		if(congress!=null)
		return congress;
		else
			return "N/A";
	}

	protected Urls(Parcel in) {
		congress = in.readString();
		govtrack = in.readString();
		opencongress = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(congress);
		dest.writeString(govtrack);
		dest.writeString(opencongress);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Urls> CREATOR = new Parcelable.Creator<Urls>() {
		@Override
		public Urls createFromParcel(Parcel in) {
			return new Urls(in);
		}

		@Override
		public Urls[] newArray(int size) {
			return new Urls[size];
		}
	};
}
