package com.harshith.hw9.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harshith.hw9.R;
import com.harshith.hw9.adapters.FragmentAdapter;
import com.harshith.hw9.models.Bill;
import com.harshith.hw9.models.Committee;
import com.harshith.hw9.models.Legislator;
import com.harshith.hw9.network.ApiServices;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment implements ApiServices.ApiResponseCallbacks{

	private TabLayout mTabLayout;
	private ViewPager mViewPager;
	ApiServices apiServices;

	private HashSet<String> favoriteLegislators = new HashSet<>();
	private HashSet<String> favoriteBills = new HashSet<>();
	private HashSet<String> favoriteCommittees = new HashSet<>();

	private ArrayList<Legislator> favLegislatorsList=new ArrayList<>();
	private ArrayList<Bill> favBillsList=new ArrayList<>();
	private ArrayList<Committee> favCommitteesList=new ArrayList<>();

	private HashMap<String, Integer> legislatorsMapIndex = new HashMap<>();

	private SharedPreferences mSharePreferences;

	private final String APP_SETTINGS="settings";

	private final String FAV_COMMITTEE_KEY="fav_committees";

	private final String FAV_LEGISLATOR_KEY="fav_legislators";

	private String TAG=getClass().getSimpleName();

	public FavoritesFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment FavoritesFragment.
	 */
	public static FavoritesFragment newInstance() {
		FavoritesFragment fragment = new FavoritesFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSharePreferences=getContext().getSharedPreferences(APP_SETTINGS,MODE_PRIVATE);
		readSharedPreferences();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_favorites, container, false);
		setupUi(view);
		apiServices=new ApiServices(this);
		apiServices.fetchLegislators();
		return view;
	}

	@Override
	public void onFetchLegislatorsSuccessful(List<Legislator> legislatorList) {
		Log.d(TAG, "onFetchLegislatorsSuccessful: ");
		apiServices.fetchBills();
		for(String bioGuideId:favoriteLegislators) {
			for(Legislator legislator:legislatorList) {
				if(legislator.getBioguideId().equalsIgnoreCase(bioGuideId)) {
					favLegislatorsList.add(legislator);
				}
			}
		}
		if(favLegislatorsList.size()<=1)
			return;
		Collections.sort(favLegislatorsList,new Comparator<Legislator>() {
			@Override
			public int compare(Legislator l1, Legislator l2) {
				return l1.getStateName().compareTo(l2.getStateName());
			}
		});
		legislatorsMapIndex=calculateIndexesForName(favLegislatorsList);
		Log.d(TAG, "onFetchLegislatorsSuccessful: fav list"+favLegislatorsList.size());
	}

	@Override
	public void onFetchLegislatorsFailure() {
		Snackbar.make(mViewPager,"Couldn't fetch legislator data",Snackbar.LENGTH_SHORT).show();
		apiServices.fetchBills();
	}

	@Override
	public void onFetchBillsSuccessful(List<Bill> billList) {
		Log.d(TAG, "onFetchBillsSuccessful: ");
		apiServices.fetchCommittees();
		for(String billId:favoriteBills) {
			for(Bill bill:billList) {
				if(bill.getBillId().equalsIgnoreCase(billId)) {
					favBillsList.add(bill);
				}
			}
		}
		if(favBillsList.size()<=1)
			return;
		Collections.sort(favBillsList, new Comparator<Bill>() {
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
		Log.d(TAG, "onFetchBillsSuccessful: favlist size"+favBillsList.size());
	}

	@Override
	public void onFetchBillsFailure() {
		Snackbar.make(mViewPager,"Couldn't fetch bills data",Snackbar.LENGTH_SHORT).show();
		apiServices.fetchCommittees();
	}

	@Override
	public void onFetchCommitteesSuccessful(List<Committee> committeeList) {
		Log.d(TAG, "onFetchCommitteesSuccessful: ");
		for(String committeeId:favoriteCommittees) {
			for(Committee committee:committeeList) {
				if(committee.getCommitteeId().equalsIgnoreCase(committeeId)) {
					favCommitteesList.add(committee);
				}
			}
		}
		if(favCommitteesList.size()<=1)
			return;
		Collections.sort(committeeList, new Comparator<Committee>() {
			@Override
			public int compare(Committee c1, Committee c2) {
				return c1.getName().compareTo(c2.getName());
			}
		});
		Log.d(TAG, "onFetchCommitteesSuccessful: committee List"+favCommitteesList.size());
		setupTabs();
	}

	@Override
	public void onFetchCommitteesFailure() {
		Snackbar.make(mViewPager,"Couldn't fetch committee data",Snackbar.LENGTH_SHORT).show();
		setupTabs();
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

	private void setupUi(View view) {
		mTabLayout=(TabLayout)view.findViewById(R.id.tabs_favorite);
		mViewPager=(ViewPager)view.findViewById(R.id.view_pager_favorites);
		mTabLayout.addTab(mTabLayout.newTab().setText("Legislators"));
		mTabLayout.addTab(mTabLayout.newTab().setText("Bills"));
		mTabLayout.addTab(mTabLayout.newTab().setText("Committees"));
	}

	public void readSharedPreferences() {
		Gson gson=new Gson();
		if(mSharePreferences.contains(FAV_COMMITTEE_KEY)) {
			String jsonList = mSharePreferences.getString(FAV_COMMITTEE_KEY, null);
			Type listType = new TypeToken<HashSet<String>>() {
			}.getType();
			favoriteCommittees = gson.fromJson(jsonList, listType);
			Log.d(TAG, "readSharedPreferences: fav comm"+gson.fromJson(jsonList, listType));
		}
		if(mSharePreferences.contains(FAV_LEGISLATOR_KEY)) {
			String jsonList = mSharePreferences.getString(FAV_LEGISLATOR_KEY, null);
			Type listType = new TypeToken<HashSet<String>>() {}.getType();
			favoriteLegislators=gson.fromJson(jsonList,listType);
			Log.d(TAG, "readSharedPreferences: fav legis"+gson.fromJson(jsonList,listType));
		}
	}

	public void setupTabs() {
		Log.d(TAG, "setupTabs: ");
		ArrayList<Fragment> fragmentList= new ArrayList<>();
		fragmentList.add(LegislatorListFragment.newInstance(favLegislatorsList,legislatorsMapIndex));
		fragmentList.add(BillsListFragment.newInstance(favBillsList));
		fragmentList.add(CommitteeListFragment.newInstance(favCommitteesList));
		String[] tabTitles={"Legislators","Bills","Committees"};
		FragmentAdapter fragmentAdapter=new FragmentAdapter(getFragmentManager(),getContext(),fragmentList,tabTitles);
		mViewPager.setAdapter(fragmentAdapter);
		mViewPager.setOffscreenPageLimit(fragmentList.size());
		mTabLayout.setupWithViewPager(mViewPager);
	}

}
