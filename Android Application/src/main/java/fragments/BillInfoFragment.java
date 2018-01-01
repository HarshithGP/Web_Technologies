package com.harshith.hw9.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harshith.hw9.R;
import com.harshith.hw9.models.Bill;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillInfoFragment extends Fragment {

	private Bill mBill;

	private TextView mBillId;
	private TextView mBillTitle;
	private TextView mBillType;
	private TextView mBillSponsor;
	private TextView mChamber;
	private TextView mBillStatus;
	private TextView mBillIntroDate;
	private TextView mCongressUrl;
	private TextView mBillVersionStatus;
	private TextView mBillUrl;

	public BillInfoFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment BillInfoFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static BillInfoFragment newInstance(Bill bill) {
		BillInfoFragment fragment = new BillInfoFragment();
		Bundle args = new Bundle();
		fragment.mBill=bill;
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_bill_info,container,false);
		setupUiComponents(view);
		populateData();
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

	private void setupUiComponents(View view) {
		mBillId=(TextView)view.findViewById(R.id.text_view_bill_id);
		mBillTitle=(TextView)view.findViewById(R.id.text_title);
		mBillType=(TextView)view.findViewById(R.id.text_bill_type);
		mBillSponsor=(TextView)view.findViewById(R.id.text_sponsor);
		mChamber=(TextView)view.findViewById(R.id.text_chamber);
		mBillStatus=(TextView)view.findViewById(R.id.text_status);
		mBillIntroDate=(TextView)view.findViewById(R.id.text_intro_date);
		mCongressUrl=(TextView)view.findViewById(R.id.text_congress_url);
		mBillVersionStatus=(TextView)view.findViewById(R.id.text_version_status);
		mBillUrl=(TextView)view.findViewById(R.id.text_bill_url);
	}

	private void populateData() {
		mBillId.setText(mBill.getBillId());
		mBillTitle.setText(mBill.getOfficialTitle());
		mBillType.setText(mBill.getBillType());
		if(mBill.getSponsor()!=null) {
			String sponsor = mBill.getSponsor().getTitle() + ". " + mBill.getSponsor().getLastName() + " " + mBill.getSponsor().getFirstName();
			mBillSponsor.setText(sponsor);
		} else {
			mBillSponsor.setText("N/A");
		}
		mChamber.setText(mBill.getChamber());
		if(mBill.getHistory()!=null) {
			if(mBill.getHistory().getActive()) {
				mBillStatus.setText("Active");
			} else {
				mBillStatus.setText("InActive");
			}
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputFormat=new SimpleDateFormat("MMM dd,yyyy");
		try {
			String date=outputFormat.format(sdf.parse(mBill.getIntroducedOn()));
			mBillIntroDate.setText(date);
		} catch (ParseException e) {
			mBillIntroDate.setText("N/A");
			e.printStackTrace();
		}
		if(mBill.getUrls()!=null) {
			mCongressUrl.setText(mBill.getUrls().getCongress());
		} else {
			mCongressUrl.setText("N/A");
		}
		if(mBill.getLastVersion()!=null) {
			mBillVersionStatus.setText(mBill.getLastVersion().getVersionName());
		} else {
			mBillVersionStatus.setText("N/A");
		}
		if(mBill.getLastVersion()!=null && mBill.getLastVersion().getUrls()!=null) {
			mBillUrl.setText(mBill.getLastVersion().getUrls().getPdf());
		} else {
			mBillUrl.setText("N/A");
		}
	}
}
