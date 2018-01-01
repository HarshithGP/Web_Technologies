package com.harshith.hw9.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.harshith.hw9.R;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentPagerAdapter {


	private Context mContext;
	private ArrayList<Fragment> fragmentList;
	String[] mTitleList;

	public FragmentAdapter(FragmentManager fm, Context mContext, ArrayList<Fragment> fragmentList,String[] tabTitles) {
		super(fm);
		this.mContext = mContext;
		this.fragmentList = fragmentList;
		mTitleList=tabTitles;
	}

	@Override
	public Fragment getItem(int position) {
		return fragmentList.get(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitleList[position];
	}

	@Override
	public int getCount() {
		if(fragmentList!=null) {
			return fragmentList.size();
		}
		else {
			return 0;
		}
	}
}
