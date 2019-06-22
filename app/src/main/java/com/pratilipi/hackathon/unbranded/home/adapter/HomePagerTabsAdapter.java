package com.pratilipi.hackathon.unbranded.home.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;


import com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.ChildFragmentHomeForyou;
import com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home.ChildFragmentHome;
import com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home.RecyclerViewExploreFragment;
import com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home.RecyclerViewHomeFragment;

import java.util.ArrayList;


public class HomePagerTabsAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener{

    //integer to count number of tabs
    int tabCount;
    String title;
    String nation;
    Activity activity;

    // tab titles
    String titles[];

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;

    //Constructor to the class
    public HomePagerTabsAdapter(FragmentManager fm, Activity activity, String titles[]) {
        super(fm);
        this.activity = activity;
        this.titles = titles;
        fragments.clear();
        fragments.add(RecyclerViewHomeFragment.newInstance());
        fragments.add(RecyclerViewExploreFragment.newInstance());
//        fragments.add(ChildFragmentHomeList.newInstance(1));
//        fragments.add(ChildFragmentHomeForyou.newInstance(2));

    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
//        DummyFragment dummyFragment = new DummyFragment();
        return fragments.get(position);

/*
        Bundle bundle = new Bundle();
        switch (position) {
            default:
                DummyFragment dummyFragment = new DummyFragment();
                return dummyFragment;
        }
*/
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

}