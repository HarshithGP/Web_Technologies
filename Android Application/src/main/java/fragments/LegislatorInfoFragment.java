package com.harshith.hw9.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.harshith.hw9.R;
import com.harshith.hw9.models.Legislator;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LegislatorInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LegislatorInfoFragment extends Fragment {

	private Legislator mLegislator;

	private ImageView mImageFavorite;
	private ImageView mFacebook;
	private ImageView mTwitter;
	private ImageView mWebsite;

	private ImageView mImageCandidatePhoto;
	private ImageView mImagePartyLogo;
	private TextView mTextViewPartyName;

	private TextView mTextViewName;
	private TextView mTextViewEmail;
	private TextView mTextViewChamber;
	private TextView mTextViewContact;
	private TextView mTextViewStartTerm;
	private TextView mTextViewEndTerm;
	private ProgressBar mProgressbarTerm;
	private TextView mTextViewOffice;
	private TextView mTextViewState;
	private TextView mTextViewFax;
	private TextView mTextViewBirthday;

	public LegislatorInfoFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment LegislatorInfoFragment.
	 */
	public static LegislatorInfoFragment newInstance(Legislator legislator) {
		LegislatorInfoFragment fragment = new LegislatorInfoFragment();
		Bundle args = new Bundle();
		fragment.mLegislator=legislator;
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_legislator_info, container, false);
		setupUiComponents(view);
		populateFields();
		return view;
	}

	private void setupUiComponents(View view) {

		mImageFavorite = (ImageView)view.findViewById(R.id.image_favorite);
		mFacebook = (ImageView)view.findViewById(R.id.image_facebook);
		mTwitter  = (ImageView)view.findViewById(R.id.image_twitter);
		mWebsite  = (ImageView)view.findViewById(R.id.image_website);

		mImageCandidatePhoto = (ImageView)view.findViewById(R.id.image_candidate_picture);
		mImagePartyLogo  = (ImageView)view.findViewById(R.id.image_party_symbol);
		mTextViewPartyName  = (TextView) view.findViewById(R.id.text_party_name);

		mTextViewName = (TextView)view.findViewById(R.id.text_name);
		mTextViewEmail = (TextView)view.findViewById(R.id.text_email);
		mTextViewChamber = (TextView)view.findViewById(R.id.text_chamber);
		mTextViewContact = (TextView)view.findViewById(R.id.text_contact);
		mTextViewStartTerm = (TextView)view.findViewById(R.id.text_start_term);
		mTextViewEndTerm = (TextView)view.findViewById(R.id.text_end_term);
		mProgressbarTerm = (ProgressBar)view.findViewById(R.id.progressBar_term);
		mTextViewOffice = (TextView)view.findViewById(R.id.text_office);
		mTextViewState = (TextView)view.findViewById(R.id.text_state);
		mTextViewFax = (TextView)view.findViewById(R.id.text_fax);
		mTextViewBirthday = (TextView)view.findViewById(R.id.text_birthday);
	}

	private void populateFields() {
		String nameString;
		//Picasso.with(getContext()).load().into(mImageCandidatePhoto);
		if(mLegislator.getParty().equalsIgnoreCase("r")) {
			mImagePartyLogo.setImageResource(R.drawable.ic_party_republican);
			mTextViewPartyName.setText("Republican");
		} else {
			mImagePartyLogo.setImageResource(R.drawable.ic_party_democrate);
			mTextViewPartyName.setText("Democratic");
		}
		nameString = mLegislator.getTitle()+". "+mLegislator.getLastName()+" "+mLegislator.getFirstName();
		mTextViewName.setText(nameString);
		mTextViewEmail.setText(mLegislator.getOcEmail());
		mTextViewChamber.setText(mLegislator.getChamber());
		mTextViewContact.setText(mLegislator.getPhone());
		mTextViewStartTerm.setText(mLegislator.getTermStart());
		mTextViewEndTerm.setText(mLegislator.getTermEnd());
		mTextViewOffice.setText(mLegislator.getOffice());
		mTextViewState.setText(mLegislator.getStateName());
		mTextViewFax.setText(mLegislator.getFax());
		mTextViewBirthday.setText(mLegislator.getBirthday());
		if(mLegislator.getFacebookId()==null) {
			mFacebook.setVisibility(View.GONE);
		} else {
			mFacebook.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					String facebookId = "https://www.facebook.com/"+mLegislator.getFacebookId();
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId));
					startActivity(browserIntent);
				}
			});
		}
		if(mLegislator.getTwitterId()==null) {
			mTwitter.setVisibility(View.GONE);
		} else {
			mTwitter.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					String twitterId="http://www.twitter.com/"+mLegislator.getTwitterId();
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterId));
					startActivity(browserIntent);
				}
			});
		}
		if(mLegislator.getWebsite()==null) {
			mWebsite.setVisibility(View.GONE);
		} else {
			mWebsite.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mLegislator.getWebsite()));
					startActivity(browserIntent);
				}
			});
		}
	}
}
