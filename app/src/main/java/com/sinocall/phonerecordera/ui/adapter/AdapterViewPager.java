package com.sinocall.phonerecordera.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.sinocall.phonerecordera.ui.fragment.FragmentBase;

import java.util.ArrayList;


public class AdapterViewPager extends FragmentPagerAdapter {
	private ArrayList<FragmentBase> fragmentBases = new ArrayList<FragmentBase>();;

	public void setFragmentBases(ArrayList<FragmentBase> fragmentBases) {
		this.fragmentBases = fragmentBases;
	}

	public AdapterViewPager(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	public AdapterViewPager(FragmentManager fragmentManager,
                            ArrayList<FragmentBase> fragmentBases) {
		super(fragmentManager);
		this.fragmentBases = fragmentBases;
	}

	@Override
	public Fragment getItem(int position) {
		return fragmentBases.get(position);
	}

	@Override
	public int getCount() {
		return fragmentBases.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		 super.destroyItem(container, position, object);
	}
}