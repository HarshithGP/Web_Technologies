package com.harshith.hw9.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Bill implements Parcelable {

	@SerializedName("bill_id")
	@Expose
	public String billId;
	@SerializedName("bill_type")
	@Expose
	public String billType;
	@SerializedName("number")
	@Expose
	public Integer number;
	@SerializedName("congress")
	@Expose
	public Integer congress;
	@SerializedName("chamber")
	@Expose
	public String chamber;
	@SerializedName("official_title")
	@Expose
	public String officialTitle;
	@SerializedName("sponsor")
	@Expose
	public Sponsor sponsor;
	@SerializedName("introduced_on")
	@Expose
	public String introducedOn;
	@SerializedName("history")
	@Expose
	public History history;
	@SerializedName("urls")
	@Expose
	public Urls urls;
	@SerializedName("last_version_on")
	@Expose
	public String lastVersionOn;
	@SerializedName("last_version")
	@Expose
	public LastVersion lastVersion;

	public String getBillId() {
		if(billId!=null)
			return billId;
		else
			return "N/A";
	}

	public String getBillType() {
		if(billType!=null)
			return billType;
		else
			return "N/A";
	}

	public Integer getNumber() {
		return number;
	}

	public Integer getCongress() {
		return congress;
	}

	public String getChamber() {
		if(chamber!=null)
			return chamber;
		else
			return "N/A";
	}

	public String getOfficialTitle() {

		if(officialTitle!=null) {
			return officialTitle;
		} else {
			return "N/A";
		}
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public String getIntroducedOn() {
		if(introducedOn!=null)
			return introducedOn;
		else
			return "N/A";
	}

	public History getHistory() {
		return history;
	}

	public Urls getUrls() {
		return urls;
	}

	public String getLastVersionOn() {
		return lastVersionOn;
	}

	public LastVersion getLastVersion() {
		return lastVersion;
	}

	protected Bill(Parcel in) {
		billId = in.readString();
		billType = in.readString();
		number = in.readByte() == 0x00 ? null : in.readInt();
		congress = in.readByte() == 0x00 ? null : in.readInt();
		chamber = in.readString();
		officialTitle = in.readString();
		sponsor = (Sponsor) in.readValue(Sponsor.class.getClassLoader());
		introducedOn = in.readString();
		history = (History) in.readValue(History.class.getClassLoader());
		urls = (Urls) in.readValue(Urls.class.getClassLoader());
		lastVersionOn = in.readString();
		lastVersion = (LastVersion) in.readValue(LastVersion.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(billId);
		dest.writeString(billType);
		if (number == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeInt(number);
		}
		if (congress == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeInt(congress);
		}
		dest.writeString(chamber);
		dest.writeString(officialTitle);
		dest.writeValue(sponsor);
		dest.writeString(introducedOn);
		dest.writeValue(history);
		dest.writeValue(urls);
		dest.writeString(lastVersionOn);
		dest.writeValue(lastVersion);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Bill> CREATOR = new Parcelable.Creator<Bill>() {
		@Override
		public Bill createFromParcel(Parcel in) {
			return new Bill(in);
		}

		@Override
		public Bill[] newArray(int size) {
			return new Bill[size];
		}
	};
}