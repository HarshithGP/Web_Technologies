package com.harshith.hw9.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harshith.hw9.R;
import com.harshith.hw9.RecyclerViewClickListener;
import com.harshith.hw9.models.Bill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillViewHolder> {

	private List<Bill> mBillsList;

	private RecyclerViewClickListener mRecyclerViewClickListener;

	public BillsAdapter(List<Bill> mBillsList,RecyclerViewClickListener recyclerViewClickListener) {
		this.mBillsList = mBillsList;
		mRecyclerViewClickListener=recyclerViewClickListener;
	}

	@Override
	public BillsAdapter.BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill,parent,false);
		return new BillViewHolder(view);
	}

	@Override
	public void onBindViewHolder(BillsAdapter.BillViewHolder holder, int position) {
		holder.mTextViewBillId.setText(mBillsList.get(position).getBillId().toUpperCase());
		holder.mTextViewBillTitle.setText(mBillsList.get(position).getOfficialTitle());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputFormat=new SimpleDateFormat("MMM dd,yyyy");
		try {
			String date=outputFormat.format(sdf.parse(mBillsList.get(position).getIntroducedOn()));
			holder.mIntroducedDate.setText(date);
		} catch (ParseException e) {
			holder.mIntroducedDate.setText("N/A");
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {
		if(mBillsList!=null) {
			return mBillsList.size();
		} else {
			return 0;
		}
	}

	public class BillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		private TextView mTextViewBillId;
		private TextView mTextViewBillTitle;
		private TextView mIntroducedDate;

		public BillViewHolder(View itemView) {
			super(itemView);
			mTextViewBillId = (TextView)itemView.findViewById(R.id.text_view_bill_id);
			mTextViewBillTitle= (TextView)itemView.findViewById(R.id.text_view_bill_title);
			mIntroducedDate= (TextView)itemView.findViewById(R.id.text_view_introduced_date);
			itemView.setClickable(true);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			mRecyclerViewClickListener.onRecyclerViewItemClicked(this.getLayoutPosition());
		}
	}
}
