package com.harshith.hw9.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harshith.hw9.CommitteeInfoActivity;
import com.harshith.hw9.R;
import com.harshith.hw9.RecyclerViewClickListener;
import com.harshith.hw9.adapters.CommitteeAdapter;
import com.harshith.hw9.models.Committee;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommitteeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommitteeListFragment extends Fragment implements RecyclerViewClickListener{

	private RecyclerView mCommitteeRecyclerView;

	private List<Committee> mCommitteeList;

	public CommitteeListFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment CommitteeListFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static CommitteeListFragment newInstance(List<Committee> committeeList) {
		CommitteeListFragment fragment = new CommitteeListFragment();
		Bundle args = new Bundle();
		fragment.mCommitteeList=committeeList;
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
		View view = inflater.inflate(R.layout.fragment_committee_list, container, false);
		setupRecyclerView(view);
		return view;
	}

	@Override
	public void onRecyclerViewItemClicked(int position) {
		Intent intent=new Intent(getContext(), CommitteeInfoActivity.class);
		intent.putExtra("committee",mCommitteeList.get(position));
		startActivity(intent);
	}

	private void setupRecyclerView(View view) {
		mCommitteeRecyclerView=(RecyclerView)view.findViewById(R.id.recycler_view_committee);
		mCommitteeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mCommitteeRecyclerView.setAdapter(new CommitteeAdapter(mCommitteeList,this));
	}

}
