package com.harshith.hw9.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sponsor implements Parcelable {

	@SerializedName("first_name")
	@Expose
	public String firstName;
	@SerializedName("last_name")
	@Expose
	public String lastName;
	@SerializedName("title")
	@Expose
	public String title;

	public String getFirstName() {
		if(firstName!=null)
		return firstName;
		else return "N/A";
	}

	public String getLastName() {
		if(lastName!=null)
		return lastName;
		else return "N/A";
	}

	public String getTitle() {
		if(title!=null)
		return title;
		else return "N/A";
	}

	protected Sponsor(Parcel in) {
		firstName = in.readString();
		lastName = in.readString();
		title = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeString(title);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Sponsor> CREATOR = new Parcelable.Creator<Sponsor>() {
		@Override
		public Sponsor createFromParcel(Parcel in) {
			return new Sponsor(in);
		}

		@Override
		public Sponsor[] newArray(int size) {
			return new Sponsor[size];
		}
	};
}