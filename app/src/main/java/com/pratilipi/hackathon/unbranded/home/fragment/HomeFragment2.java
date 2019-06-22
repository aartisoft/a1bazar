package com.pratilipi.hackathon.unbranded.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.pratilipi.hackathon.unbranded.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class HomeFragment2 extends Fragment {

    @BindView(R.id.tl_hometabs)
    TabLayout tlHometabs;
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    Unbinder unbinder;

    String titles[] = {"First", "Second", "Third"};

/*
    @BindView(R.id.recycler_tab_layout)
    RecyclerTabLayout recyclerTabLayout;
*/


    public static HomeFragment2 newInstance(int index) {
        HomeFragment2 fragment = new HomeFragment2();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_home, container, false);
        unbinder = ButterKnife.bind(this, view);

/*
        HomePagerTabsAdapter profileAdapter = new HomePagerTabsAdapter(getChildFragmentManager(), getActivity(), titles);
        vpHome.setOffscreenPageLimit(2);
        vpHome.setAdapter(profileAdapter);
        tlHometabs.setupWithViewPager(vpHome);
//        recyclerTabLayout.setUpWithViewPager(vpHome);


        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                vpHome.setCurrentItem(position);
                Toast.makeText(getActivity(),
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
*/


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

