package com.harshith.hw9.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommitteeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommitteeFragment extends Fragment implements ApiServices.ApiResponseCallbacks{

	private TabLayout mTabLayout;
	private ViewPager mViewPager;
	private ProgressBar mProgressBar;

	private List<Committee> houseCommitteeList = new ArrayList<>();
	private List<Committee> senateCommitteeList = new ArrayList<>();
	private List<Committee> jointCommitteeList = new ArrayList<>();

	private ApiServices apiService;

	public CommitteeFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment CommitteeFragment.
	 */
	public static CommitteeFragment newInstance() {
		CommitteeFragment fragment = new CommitteeFragment();
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
		View view= inflater.inflate(R.layout.fragment_committee, container, false);
		setupUiComponents(view);
		mProgressBar.setVisibility(View.VISIBLE);
		apiService=new ApiServices(this);
		apiService.fetchCommittees();
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

	}

	@Override
	public void onFetchBillsFailure() {

	}

	@Override
	public void onFetchCommitteesSuccessful(List<Committee> committeeList) {
		Collections.sort(committeeList, new Comparator<Committee>() {
			@Override
			public int compare(Committee c1, Committee c2) {
				return c1.getName().compareTo(c2.getName());
			}
		});
		for(Committee committee:committeeList) {
			if(committee.getChamber().equalsIgnoreCase("house")) {
				houseCommitteeList.add(committee);
			} else if(committee.getChamber().equalsIgnoreCase("senate")) {
				senateCommitteeList.add(committee);
			} else if(committee.getChamber().equalsIgnoreCase("joint")) {
				jointCommitteeList.add(committee);
			}
		}
		mProgressBar.setVisibility(View.GONE);
		setUpTabs();
	}

	@Override
	public void onFetchCommitteesFailure() {
		mProgressBar.setVisibility(View.GONE);
		Snackbar.make(mViewPager,"Couldn't fetch committee data",Snackbar.LENGTH_SHORT)
				.setAction("Retry", new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						apiService.fetchCommittees();
					}
				}).show();
	}

	private void setupUiComponents(View view) {
		mTabLayout=(TabLayout)view.findViewById(R.id.tabs_committee);
		mViewPager=(ViewPager)view.findViewById(R.id.view_pager_committee);
		mProgressBar=(ProgressBar)view.findViewById(R.id.progress_bar);

		mTabLayout.addTab(mTabLayout.newTab().setText("By State"));
		mTabLayout.addTab(mTabLayout.newTab().setText("House"));
		mTabLayout.addTab(mTabLayout.newTab().setText("Senate"));
	}

	private void setUpTabs() {
		ArrayList<Fragment> fragmentList = new ArrayList<>();
		fragmentList.add(CommitteeListFragment.newInstance(houseCommitteeList));
		fragmentList.add(CommitteeListFragment.newInstance(senateCommitteeList));
		fragmentList.add(CommitteeListFragment.newInstance(jointCommitteeList));
		String[] tabTitles={"House","Senate","Joint"};
		FragmentAdapter adapter=new FragmentAdapter(getFragmentManager(),getContext(),fragmentList,tabTitles);
		mViewPager.setAdapter(adapter);
		mViewPager.setOffscreenPageLimit(fragmentList.size());
		mTabLayout.setupWithViewPager(mViewPager);
	}
}
