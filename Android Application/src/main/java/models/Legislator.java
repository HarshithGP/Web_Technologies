package com.harshith.hw9.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Legislator implements Parcelable {
	@SerializedName("bioguide_id")
	@Expose
	public String bioguideId;
	@SerializedName("in_office")
	@Expose
	public Boolean inOffice;
	@SerializedName("thomas_id")
	@Expose
	public String thomasId;
	@SerializedName("govtrack_id")
	@Expose
	public String govtrackId;
	@SerializedName("crp_id")
	@Expose
	public String crpId;
	@SerializedName("fec_ids")
	@Expose
	public List<String> fecIds = new ArrayList<String>();
	@SerializedName("first_name")
	@Expose
	public String firstName;
	@SerializedName("nickname")
	@Expose
	public String nickname;
	@SerializedName("last_name")
	@Expose
	public String lastName;
	@SerializedName("middle_name")
	@Expose
	public String middleName;
	@SerializedName("name_suffix")
	@Expose
	public String nameSuffix;
	@SerializedName("gender")
	@Expose
	public String gender;
	@SerializedName("birthday")
	@Expose
	public String birthday;
	@SerializedName("leadership_role")
	@Expose
	public String leadershipRole;
	@SerializedName("term_start")
	@Expose
	public String termStart;
	@SerializedName("term_end")
	@Expose
	public String termEnd;
	@SerializedName("state")
	@Expose
	public String state;
	@SerializedName("state_name")
	@Expose
	public String stateName;
	@SerializedName("party")
	@Expose
	public String party;
	@SerializedName("title")
	@Expose
	public String title;
	@SerializedName("chamber")
	@Expose
	public String chamber;
	@SerializedName("phone")
	@Expose
	public String phone;
	@SerializedName("fax")
	@Expose
	public String fax;
	@SerializedName("website")
	@Expose
	public String website;
	@SerializedName("office")
	@Expose
	public String office;
	@SerializedName("contact_form")
	@Expose
	public String contactForm;
	@SerializedName("votesmart_id")
	@Expose
	public Integer votesmartId;
	@SerializedName("icpsr_id")
	@Expose
	public Integer icpsrId;
	@SerializedName("senate_class")
	@Expose
	public Integer senateClass;
	@SerializedName("lis_id")
	@Expose
	public String lisId;
	@SerializedName("state_rank")
	@Expose
	public String stateRank;
	@SerializedName("district")
	@Expose
	public Integer district;
	@SerializedName("oc_email")
	@Expose
	public String ocEmail;
	@SerializedName("twitter_id")
	@Expose
	public String twitterId;
	@SerializedName("youtube_id")
	@Expose
	public String youtubeId;
	@SerializedName("facebook_id")
	@Expose
	public String facebookId;
	@SerializedName("ocd_id")
	@Expose
	public String ocdId;

	public String getBioguideId() {
		return bioguideId;
	}

	public Boolean getInOffice() {
		return inOffice;
	}

	public String getThomasId() {
		return thomasId;
	}

	public String getGovtrackId() {
		return govtrackId;
	}

	public String getCrpId() {
		return crpId;
	}

	public List<String> getFecIds() {
		return fecIds;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getNickname() {
		return nickname;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public String getGender() {
		return gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getLeadershipRole() {
		return leadershipRole;
	}

	public String getTermStart() {
		return termStart;
	}

	public String getTermEnd() {
		return termEnd;
	}

	public String getState() {
		return state;
	}

	public String getStateName() {
		return stateName;
	}

	public String getParty() {
		return party;
	}

	public String getTitle() {
		return title;
	}

	public String getChamber() {
		return chamber;
	}

	public String getPhone() {
		return phone;
	}

	public String getFax() {
		return fax;
	}

	public String getWebsite() {
		return website;
	}

	public String getOffice() {
		return office;
	}

	public String getContactForm() {
		return contactForm;
	}

	public Integer getVotesmartId() {
		return votesmartId;
	}

	public Integer getIcpsrId() {
		return icpsrId;
	}

	public Integer getSenateClass() {
		return senateClass;
	}

	public String getLisId() {
		return lisId;
	}

	public String getStateRank() {
		return stateRank;
	}

	public Integer getDistrict() {
		return district;
	}

	public String getOcEmail() {
		return ocEmail;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public String getYoutubeId() {
		return youtubeId;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public String getOcdId() {
		return ocdId;
	}

	protected Legislator(Parcel in) {
		bioguideId = in.readString();
		byte inOfficeVal = in.readByte();
		inOffice = inOfficeVal == 0x02 ? null : inOfficeVal != 0x00;
		thomasId = in.readString();
		govtrackId = in.readString();
		crpId = in.readString();
		if (in.readByte() == 0x01) {
			fecIds = new ArrayList<String>();
			in.readList(fecIds, String.class.getClassLoader());
		} else {
			fecIds = null;
		}
		firstName = in.readString();
		nickname = in.readString();
		lastName = in.readString();
		middleName = in.readString();
		nameSuffix = in.readString();
		gender = in.readString();
		birthday = in.readString();
		leadershipRole = in.readString();
		termStart = in.readString();
		termEnd = in.readString();
		state = in.readString();
		stateName = in.readString();
		party = in.readString();
		title = in.readString();
		chamber = in.readString();
		phone = in.readString();
		fax = in.readString();
		website = in.readString();
		office = in.readString();
		contactForm = in.readString();
		votesmartId = in.readByte() == 0x00 ? null : in.readInt();
		icpsrId = in.readByte() == 0x00 ? null : in.readInt();
		senateClass = in.readByte() == 0x00 ? null : in.readInt();
		lisId = in.readString();
		stateRank = in.readString();
		district = in.readByte() == 0x00 ? null : in.readInt();
		ocEmail = in.readString();
		twitterId = in.readString();
		youtubeId = in.readString();
		facebookId = in.readString();
		ocdId = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(bioguideId);
		if (inOffice == null) {
			dest.writeByte((byte) (0x02));
		} else {
			dest.writeByte((byte) (inOffice ? 0x01 : 0x00));
		}
		dest.writeString(thomasId);
		dest.writeString(govtrackId);
		dest.writeString(crpId);
		if (fecIds == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeList(fecIds);
		}
		dest.writeString(firstName);
		dest.writeString(nickname);
		dest.writeString(lastName);
		dest.writeString(middleName);
		dest.writeString(nameSuffix);
		dest.writeString(gender);
		dest.writeString(birthday);
		dest.writeString(leadershipRole);
		dest.writeString(termStart);
		dest.writeString(termEnd);
		dest.writeString(state);
		dest.writeString(stateName);
		dest.writeString(party);
		dest.writeString(title);
		dest.writeString(chamber);
		dest.writeString(phone);
		dest.writeString(fax);
		dest.writeString(website);
		dest.writeString(office);
		dest.writeString(contactForm);
		if (votesmartId == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeInt(votesmartId);
		}
		if (icpsrId == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeInt(icpsrId);
		}
		if (senateClass == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeInt(senateClass);
		}
		dest.writeString(lisId);
		dest.writeString(stateRank);
		if (district == null) {
			dest.writeByte((byte) (0x00));
		} else {
			dest.writeByte((byte) (0x01));
			dest.writeInt(district);
		}
		dest.writeString(ocEmail);
		dest.writeString(twitterId);
		dest.writeString(youtubeId);
		dest.writeString(facebookId);
		dest.writeString(ocdId);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Legislator> CREATOR = new Parcelable.Creator<Legislator>() {
		@Override
		public Legislator createFromParcel(Parcel in) {
			return new Legislator(in);
		}

		@Override
		public Legislator[] newArray(int size) {
			return new Legislator[size];
		}
	};
}