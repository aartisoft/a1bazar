package com.pratilipi.hackathon.unbranded.home.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 *
 */
public class HomeFragmentViewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragments = new ArrayList<>();
	private Fragment currentFragment;

	public HomeFragmentViewPagerAdapter(FragmentManager fm) {
		super(fm);

		fragments.clear();
		fragments.add(HomeFragment.newInstance(0));
//		fragments.add(LibraryFragment.newInstance(1));
		fragments.add(NavCreateFragment.newInstance(2));
//		fragments.add(NavUpdatesFragment.newInstance(3));
		fragments.add(NavProfileFragment.newInstance(4));
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		if (getCurrentFragment() != object) {
			currentFragment = ((Fragment) object);
		}
		super.setPrimaryItem(container, position, object);
	}

	/**
	 * Get the current fragment
	 */
	public Fragment getCurrentFragment() {
		return currentFragment;
	}
	public Fragment setCurrentFragment(int index) {
		return fragments.get(index);
	}
}