package com.harshith.hw9.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harshith.hw9.LegislatorInfoActivity;
import com.harshith.hw9.R;
import com.harshith.hw9.RecyclerViewClickListener;
import com.harshith.hw9.adapters.LegislatorAdapter;
import com.harshith.hw9.models.Legislator;
import com.harshith.hw9.thirdPartyComponents.FastScrollRecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LegislatorListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LegislatorListFragment extends Fragment implements RecyclerViewClickListener {

	private List<Legislator> myDataSet = new ArrayList<>();

	private HashMap<String, Integer> mapIndex=new HashMap<>();

	private RecyclerView mByStatesRecylcerView;

	public LegislatorListFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment LegislatorListFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static LegislatorListFragment newInstance(List<Legislator> dataSet, HashMap<String,Integer> mapIndex) {
		LegislatorListFragment fragment = new LegislatorListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		fragment.myDataSet=dataSet;
		fragment.mapIndex=mapIndex;
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
		View view = inflater.inflate(R.layout.fragment_by_state, container, false);
		setupUi(view);
		return view;
	}

	@Override
	public void onRecyclerViewItemClicked(int position) {
		/*getActivity().getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.tab_layout_container,LegislatorInfoFragment.newInstance(myDataSet.get(position)),null)
				.addToBackStack("legislatorInfo")
				.commit();*/
		Intent intent=new Intent(getContext(), LegislatorInfoActivity.class);
		intent.putExtra("legislator",myDataSet.get(position));
		startActivity(intent);
	}

	private void setupUi(View view) {
		mByStatesRecylcerView = (RecyclerView) view.findViewById(R.id.recycler_view_by_state);
		mByStatesRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
		FastScrollRecyclerViewItemDecoration decoration = new FastScrollRecyclerViewItemDecoration(getContext());
		LegislatorAdapter adapter=new LegislatorAdapter(getContext(),myDataSet,mapIndex,this);
		mByStatesRecylcerView.setAdapter(adapter);
		mByStatesRecylcerView.addItemDecoration(decoration);
		mByStatesRecylcerView.setItemAnimator(new DefaultItemAnimator());
	}

}
