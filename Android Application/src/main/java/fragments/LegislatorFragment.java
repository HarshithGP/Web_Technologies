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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LegislatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LegislatorFragment extends Fragment implements ApiServices.ApiResponseCallbacks{

	private List<Legislator> mLegislatorsList = new ArrayList<>();
	ArrayList<Legislator> mByStateList = new ArrayList<>();
	private List<Legislator> mHouseList = new ArrayList<>();
	private List<Legislator> mSenateList = new ArrayList<>();

	private HashMap<String, Integer> legislatorsMapIndex = new HashMap<>();
	private HashMap<String, Integer> houseMapIndex = new HashMap<>();
	private HashMap<String, Integer> senateMapIndex = new HashMap<>();

	private TabLayout mTabLayout;
	private ViewPager mViewPager;
	private ProgressBar mProgressBar;

	private ApiServices apiServices;

	public LegislatorFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment LegislatorFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static LegislatorFragment newInstance() {
		LegislatorFragment fragment = new LegislatorFragment();
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
		View view= inflater.inflate(R.layout.fragment_legislator, container, false);
		setupUiComponents(view);
		mProgressBar.setVisibility(View.VISIBLE);
		apiServices=new ApiServices(this);
		apiServices.fetchLegislators();
		return view;
	}

	@Override
	public void onFetchLegislatorsSuccessful(List<Legislator> legislatorList) {
		mLegislatorsList=legislatorList;
		setupData();
		mProgressBar.setVisibility(View.GONE);
		setupTabs();
	}

	@Override
	public void onFetchLegislatorsFailure() {
		mProgressBar.setVisibility(View.GONE);
		Snackbar.make(mViewPager,"Couldn't fetch legislator data",Snackbar.LENGTH_SHORT)
				.setAction("Retry", new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						apiServices.fetchLegislators();
					}
				}).show();
	}

	@Override
	public void onFetchBillsSuccessful(List<Bill> billList) {

	}

	@Override
	public void onFetchBillsFailure() {

	}

	@Override
	public void onFetchCommitteesSuccessful(List<Committee> committeeList) {

	}

	@Override
	public void onFetchCommitteesFailure() {

	}

	/**
	 * Extract data received from api to pass it to appropriate fragments
	 */
	private void setupData() {
		//first sort the list based on state for By State Tab
		mByStateList = new ArrayList<>(mLegislatorsList);
		Collections.sort(mByStateList,new Comparator<Legislator>() {
			@Override
			public int compare(Legislator l1, Legislator l2) {
				return l1.getStateName().compareTo(l2.getStateName());
			}
		});
		legislatorsMapIndex = calculateIndexesForName(mByStateList);
		//sort the list based on last name for house and senate
		Collections.sort(mLegislatorsList, new Comparator<Legislator>() {
			@Override
			public int compare(Legislator l1, Legislator l2) {
				return l1.getLastName().compareTo(l2.getLastName());
			}
		});
		//Separate data into house and senate
		for(Legislator legislator:mLegislatorsList) {
			String chamber=legislator.getChamber();
			if(chamber.equalsIgnoreCase("house")) {
				mHouseList.add(legislator);
			} else if(chamber.equalsIgnoreCase("senate")){
				mSenateList.add(legislator);
			}
		}
		//prepare index for recyclerview
		houseMapIndex = calculateIndexesForName(mHouseList);
		senateMapIndex = calculateIndexesForName(mSenateList);
	}

	private void setupUiComponents(View view) {
		mTabLayout=(TabLayout)view.findViewById(R.id.tabs_legislator);
		mViewPager=(ViewPager)view.findViewById(R.id.view_pager_legislator);
		mProgressBar=(ProgressBar)view.findViewById(R.id.progress_bar);
		mTabLayout.addTab(mTabLayout.newTab().setText("By State"));
		mTabLayout.addTab(mTabLayout.newTab().setText("House"));
		mTabLayout.addTab(mTabLayout.newTab().setText("Senate"));
	}

	private HashMap<String, Integer> calculateIndexesForName(List<Legislator> items){
		HashMap<String, Integer> mapIndex = new LinkedHashMap<String, Integer>();
		for (int i = 0; i<items.size(); i++){
			String name = items.get(i).stateName;
			String index = name.substring(0,1);
			index = index.toUpperCase();

			if (!mapIndex.containsKey(index)) {
				mapIndex.put(index, i);
			}
		}
		return mapIndex;
	}

	private void setupTabs() {
		ArrayList<Fragment> fragmentList = new ArrayList<>();
		fragmentList.add(LegislatorListFragment.newInstance(mByStateList, legislatorsMapIndex));
		fragmentList.add(LegislatorListFragment.newInstance(mHouseList, houseMapIndex));
		fragmentList.add(LegislatorListFragment.newInstance(mSenateList, senateMapIndex));
		String[] tabTitles={"By State","House","Senate"};
		FragmentAdapter adapter=new FragmentAdapter(getFragmentManager(),getContext(),fragmentList,tabTitles);
		mViewPager.setAdapter(adapter);
		mViewPager.setOffscreenPageLimit(fragmentList.size());
		mTabLayout.setupWithViewPager(mViewPager);
	}
}
