package com.harshith.hw9.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harshith.hw9.R;
import com.harshith.hw9.RecyclerViewClickListener;
import com.harshith.hw9.models.Committee;

import java.util.List;

public class CommitteeAdapter extends RecyclerView.Adapter<CommitteeAdapter.CommitteeViewHolder> {

	private List<Committee> mCommitteeList;
	private RecyclerViewClickListener mRecyclerViewClickListener;

	public CommitteeAdapter(List<Committee> committeeList, RecyclerViewClickListener recyclerViewClickListener) {
		this.mCommitteeList = committeeList;
		mRecyclerViewClickListener=recyclerViewClickListener;
	}

	@Override
	public CommitteeAdapter.CommitteeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_committee,parent,false);
		return new CommitteeViewHolder(view);
	}

	@Override
	public void onBindViewHolder(CommitteeAdapter.CommitteeViewHolder holder, int position) {
		holder.mCommitteeId.setText(mCommitteeList.get(position).getCommitteeId());
		holder.mCommitteeName.setText(mCommitteeList.get(position).getName());
		holder.mCommitteeChamber.setText(mCommitteeList.get(position).getChamber());
	}

	@Override
	public int getItemCount() {
		if(mCommitteeList!=null) {
			return mCommitteeList.size();
		} else {
			return 0;
		}
	}

	public class CommitteeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		private TextView mCommitteeId;
		private TextView mCommitteeName;
		private TextView mCommitteeChamber;

		@Override
		public void onClick(View view) {
			mRecyclerViewClickListener.onRecyclerViewItemClicked(this.getLayoutPosition());
		}

		public CommitteeViewHolder(View itemView) {
			super(itemView);
			mCommitteeId=(TextView)itemView.findViewById(R.id.text_view_committee_id);
			mCommitteeName=(TextView)itemView.findViewById(R.id.text_view_committee_name);
			mCommitteeChamber=(TextView)itemView.findViewById(R.id.text_view_chamber);
			itemView.setClickable(true);
			itemView.setOnClickListener(this);
		}
	}
}
