package com.harshith.hw9.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class History implements Parcelable {

	@SerializedName("active")
	@Expose
	public Boolean active;
	@SerializedName("active_at")
	@Expose
	public String activeAt;
	@SerializedName("awaiting_signature")
	@Expose
	public Boolean awaitingSignature;
	@SerializedName("senate_passage_result")
	@Expose
	public String senatePassageResult;
	@SerializedName("senate_passage_result_at")
	@Expose
	public String senatePassageResultAt;

	public Boolean getActive() {
		return active;
	}

	protected History(Parcel in) {
		byte activeVal = in.readByte();
		active = activeVal == 0x02 ? null : activeVal != 0x00;
		activeAt = in.readString();
		byte awaitingSignatureVal = in.readByte();
		awaitingSignature = awaitingSignatureVal == 0x02 ? null : awaitingSignatureVal != 0x00;
		senatePassageResult = in.readString();
		senatePassageResultAt = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if (active == null) {
			dest.writeByte((byte) (0x02));
		} else {
			dest.writeByte((byte) (active ? 0x01 : 0x00));
		}
		dest.writeString(activeAt);
		if (awaitingSignature == null) {
			dest.writeByte((byte) (0x02));
		} else {
			dest.writeByte((byte) (awaitingSignature ? 0x01 : 0x00));
		}
		dest.writeString(senatePassageResult);
		dest.writeString(senatePassageResultAt);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<History> CREATOR = new Parcelable.Creator<History>() {
		@Override
		public History createFromParcel(Parcel in) {
			return new History(in);
		}

		@Override
		public History[] newArray(int size) {
			return new History[size];
		}
	};
}