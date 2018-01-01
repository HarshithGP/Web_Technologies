package com.harshith.hw9.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Committee implements Parcelable {
	@SerializedName("committee_id")
	@Expose
	public String committeeId;
	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("chamber")
	@Expose
	public String chamber;
	@SerializedName("subcommittee")
	@Expose
	public Boolean subcommittee;
	@SerializedName("parent_committee_id")
	@Expose
	public String parentCommitteeId;
	@SerializedName("phone")
	@Expose
	public String phone;
	@SerializedName("office")
	@Expose
	public String office;

	public String getCommitteeId() {
		if(committeeId!=null)
		return committeeId;
		else
			return "N/A";
	}

	public String getName() {
		if(name!=null)
		return name;
		else
			return "N/A";
	}

	public String getChamber() {
		if(chamber!=null)
		return chamber;
		else
			return "N/A";
	}

	public Boolean getSubcommittee() {
		return subcommittee;
	}

	public String getParentCommitteeId() {
		if(parentCommitteeId!=null)
		return parentCommitteeId;
		else
			return "N/A";
	}

	public String getPhone() {
		if(phone!=null)
		return phone;
		else
			return "N/A";
	}

	public String getOffice() {
		if(office!=null)
		return office;
		else
			return "N/A";
	}

	protected Committee(Parcel in) {
		committeeId = in.readString();
		name = in.readString();
		chamber = in.readString();
		byte subcommitteeVal = in.readByte();
		subcommittee = subcommitteeVal == 0x02 ? null : subcommitteeVal != 0x00;
		parentCommitteeId = in.readString();
		phone = in.readString();
		office = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(committeeId);
		dest.writeString(name);
		dest.writeString(chamber);
		if (subcommittee == null) {
			dest.writeByte((byte) (0x02));
		} else {
			dest.writeByte((byte) (subcommittee ? 0x01 : 0x00));
		}
		dest.writeString(parentCommitteeId);
		dest.writeString(phone);
		dest.writeString(office);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Committee> CREATOR = new Parcelable.Creator<Committee>() {
		@Override
		public Committee createFromParcel(Parcel in) {
			return new Committee(in);
		}

		@Override
		public Committee[] newArray(int size) {
			return new Committee[size];
		}
	};
}