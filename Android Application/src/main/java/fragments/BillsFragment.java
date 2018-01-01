package com.harshith.hw9.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.harshith.hw9.R;
import com.harshith.hw9.adapters.FragmentAdapter;
import com.harshith.hw9.models.Bill;
import com.harshith.hw9.models.Committee;
import com.harshith.hw9.models.Legislator;
import com.harshith.hw9.network.ApiServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillsFragment extends Fragment implements ApiServices.ApiResponseCallbacks{

	private TabLayout mTabLayout;
	private ViewPager mViewPager;

	private ArrayList<Bill> mActiveBills=new ArrayList<>();
	private ArrayList<Bill> mNewBills=new ArrayList<>();
	private ProgressBar mProgressBar;

	private ApiServices apiServices;

	public BillsFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment BillsFragment.
	 */

	public static BillsFragment newInstance() {
		BillsFragment fragment = new BillsFragment();
		Bundle args = new Bundle();
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
		View view= inflater.inflate(R.layout.fragment_bills, container, false);
		setupUi(view);
		mProgressBar.setVisibility(View.VISIBLE);
		apiServices=new ApiServices(this);
		apiServices.fetchBills();
		return view;
	}

	@Override
	public void onFetchLegislatorsSuccessful(List<Legislator> legislatorList) {

	}

	@Override
	public void onFetchLegislatorsFailure() {

	}

	@Override
	public void onFetchBillsSuccessful(List<Bill> billList) {
		Collections.sort(billList, new Comparator<Bill>() {
			@Override
			public int compare(Bill b1, Bill b2) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date d1,d2;
				try {
					d1=sdf.parse(b1.getIntroducedOn());
					d2=sdf.parse(b2.getIntroducedOn());
					if(d1.compareTo(d2)==1)
						return -1;
					else if(d1.compareTo(d2)==-1)
						return 1;
					else
						return 0;
				} catch (ParseException e) {
					e.printStackTrace();
					return 0;
				}
			}
		});
		for(Bill bill:billList) {
			if(bill.getHistory().getActive()) {
				mActiveBills.add(bill);
			} else {
				mNewBills.add(bill);
			}
		}
		mProgressBar.setVisibility(View.GONE);
		setupTabs();
	}

	@Override
	public void onFetchBillsFailure() {
		mProgressBar.setVisibility(View.GONE);
		Snackbar.make(mViewPager,"Couldn't fetch bills data",Snackbar.LENGTH_SHORT)
				.setAction("Retry", new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						apiServices.fetchBills();
					}
				}).show();
	}

	@Override
	public void onFetchCommitteesSuccessful(List<Committee> committeeList) {

	}

	@Override
	public void onFetchCommitteesFailure() {

	}

	private void setupUi(View view) {
		mTabLayout=(TabLayout)view.findViewById(R.id.tabs_bills);
		mViewPager=(ViewPager)view.findViewById(R.id.view_pager_bills);
		mProgressBar=(ProgressBar)view.findViewById(R.id.progress_bar);
		mTabLayout.addTab(mTabLayout.newTab().setText("Active Bills"));
		mTabLayout.addTab(mTabLayout.newTab().setText("New Bills"));
	}

	private void setupTabs() {
		ArrayList<Fragment> fragmentList = new ArrayList<>();
		fragmentList.add(BillsListFragment.newInstance(mActiveBills));
		fragmentList.add(BillsListFragment.newInstance(mNewBills));
		String[] tabTitles={"Active Bills","New Bills"};
		FragmentAdapter adapter=new FragmentAdapter(getFragmentManager(),getContext(),fragmentList,tabTitles);
		mViewPager.setAdapter(adapter);
		mViewPager.setOffscreenPageLimit(fragmentList.size());
		mTabLayout.setupWithViewPager(mViewPager);
	}

}
