package com.harshith.hw9.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harshith.hw9.R;
import com.harshith.hw9.RecyclerViewClickListener;
import com.harshith.hw9.fragments.LegislatorInfoFragment;
import com.harshith.hw9.models.Legislator;
import com.harshith.hw9.thirdPartyComponents.FastScrollRecyclerViewInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LegislatorAdapter extends RecyclerView.Adapter<LegislatorAdapter.LegislatorViewHolder>
		implements FastScrollRecyclerViewInterface {

	private List<Legislator> dataSet;
	private HashMap<String, Integer> mMapIndex;
	private Context mContext;
	private RecyclerViewClickListener mRecyclerViewClickListener;

	public LegislatorAdapter(Context context, List<Legislator> dataSet, HashMap<String, Integer> mapIndex,RecyclerViewClickListener recyclerViewClickListener) {
		this.dataSet=dataSet;
		this.mMapIndex=mapIndex;
		mContext=context;
		mRecyclerViewClickListener=recyclerViewClickListener;

	}

	@Override
	public LegislatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_legislator,parent,false);
		return new LegislatorViewHolder(v);
	}

	@Override
	public void onBindViewHolder(LegislatorViewHolder holder, int position) {
		holder.textViewName.setText(dataSet.get(position).getLastName() + ", " + dataSet.get(position).getFirstName());
		String details=prepareDetails(position);
		holder.textViewDetails.setText(details);
		Picasso.with(mContext)
				.load("https://theunitedstates.io/images/congress/original/"+dataSet.get(position).getBioguideId()+".jpg")
				.into(holder.imageThumbNail);
	}

	@Override
	public int getItemCount() {
		if(dataSet!=null)
			return dataSet.size();
		else
			return 0;
	}

	@Override
	public HashMap<String, Integer> getMapIndex() {
		return this.mMapIndex;
	}

	private String prepareDetails(int position) {
		StringBuilder detailsString = new StringBuilder();
		detailsString.append("("+dataSet.get(position).getParty()+")");
		detailsString.append(dataSet.get(position).getStateName());
		if(dataSet.get(position).getDistrict()!=null) {
			detailsString.append(" District - " + dataSet.get(position).getDistrict());
		} else {
			detailsString.append(" District - N/A" );
		}
		return detailsString.toString();
	}

	public class LegislatorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		TextView textViewName;
		TextView textViewDetails;
		ImageView imageThumbNail;

		public LegislatorViewHolder(View itemView) {
			super(itemView);
			textViewName=(TextView)itemView.findViewById(R.id.text_view_name);
			textViewDetails=(TextView)itemView.findViewById(R.id.subHeader);
			imageThumbNail=(ImageView)itemView.findViewById(R.id.image_view_thumbnail);
			itemView.setClickable(true);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			mRecyclerViewClickListener.onRecyclerViewItemClicked(this.getLayoutPosition());
		}
	}
}
