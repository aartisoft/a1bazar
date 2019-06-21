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
import com.pratilipi.hackathon.unbranded.home.adapter.HomePagerTabsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.tl_hometabs)
    TabLayout tlHometabs;

    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    Unbinder unbinder;

    String titles[] = {"Trending", "Explore"};

/*
    @BindView(R.id.recycler_tab_layout)
    RecyclerTabLayout recyclerTabLayout;
*/


    public static HomeFragment newInstance(int index) {
        HomeFragment fragment = new HomeFragment();
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

        HomePagerTabsAdapter myAdapter = new HomePagerTabsAdapter(getChildFragmentManager(), getActivity(), titles);
        vpHome.setOffscreenPageLimit(3);
        vpHome.setAdapter(myAdapter);
        tlHometabs.setupWithViewPager(vpHome);
//        recyclerTabLayout.setUpWithViewPager(vpHome);


        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                vpHome.setCurrentItem(position);
/*
                Toast.makeText(getActivity(),
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
*/
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


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

