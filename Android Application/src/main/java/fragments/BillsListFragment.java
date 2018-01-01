package com.harshith.hw9.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harshith.hw9.BillInfoActivity;
import com.harshith.hw9.R;
import com.harshith.hw9.RecyclerViewClickListener;
import com.harshith.hw9.adapters.BillsAdapter;
import com.harshith.hw9.models.Bill;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillsListFragment extends Fragment implements RecyclerViewClickListener{

	private RecyclerView mBillsRecyclerView;

	private List<Bill> mBillList;

	public BillsListFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment BillsListFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static BillsListFragment newInstance(List<Bill> billList) {
		BillsListFragment fragment = new BillsListFragment();
		Bundle args = new Bundle();
		fragment.mBillList=billList;
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
		View view= inflater.inflate(R.layout.fragment_active_bills, container, false);
		setupUi(view);
		return view;
	}

	@Override
	public void onRecyclerViewItemClicked(int position) {
		/*getActivity().getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.tab_layout_container,BillInfoFragment.newInstance(mBillList.get(position)),null)
				.addToBackStack("legislatorInfo")
				.commit();*/
		Intent intent=new Intent(getContext(), BillInfoActivity.class);
		intent.putExtra("bill",mBillList.get(position));
		startActivity(intent);
	}

	private void setupUi(View view) {
		mBillsRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_bills);
		mBillsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mBillsRecyclerView.setAdapter(new BillsAdapter(mBillList,this));
	}

}
