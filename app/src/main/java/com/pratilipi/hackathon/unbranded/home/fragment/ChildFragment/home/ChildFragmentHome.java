package com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;


import com.pratilipi.hackathon.unbranded.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class ChildFragmentHome extends Fragment
//        implements ObservableScrollViewCallbacks
{


    Unbinder unbinder;

    @BindView(R.id.fragment_demo_recycler_view)
    RecyclerView fragmentDemoRecyclerView;

/*
    @BindView(R.id.scroll)
    ObservableScrollView mScrollView;
*/

    private RecyclerView.LayoutManager layoutManager;
    private FrameLayout fragmentContainer;

    View bottomNavigation;

    /**
     * Create a new instance of the fragment
     */
    public static ChildFragmentHome newInstance(int index) {
        ChildFragmentHome fragment = new ChildFragmentHome();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_child_home, container, false);
//        initDemoList(view);
        unbinder = ButterKnife.bind(this, view);

//        mScrollView.setScrollViewCallbacks(this);

        setupRecyclerView();

        return view;

    }

    private void setupRecyclerView() {
/*
        fragmentDemoRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        fragmentDemoRecyclerView.setLayoutManager(layoutManager);

        ArrayList<String> itemsData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            itemsData.add("Fragment " + getArguments().getInt("index", -1) + " / Item " + i);
        }

        DemoAdapter adapter = new DemoAdapter(itemsData);
        fragmentDemoRecyclerView.setAdapter(adapter);
*/
//        fragmentDemoRecyclerView.smoothScrollToPosition(0);


    }



    /**
     * Init the fragment
     */
/*
    private void initDemoList(View view) {

        fragmentContainer = view.findViewById(R.id.fragment_container);
        recyclerView = view.findViewById(R.id.fragment_demo_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<String> itemsData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            itemsData.add("Fragment " + getArguments().getInt("index", -1) + " / Item " + i);
        }

        DemoAdapter adapter = new DemoAdapter(itemsData);
        recyclerView.setAdapter(adapter);
    }
*/

    /**
     * Refresh
     */
    public void refresh() {
        if (getArguments().getInt("index", 0) > 0 && fragmentDemoRecyclerView != null) {
            fragmentDemoRecyclerView.smoothScrollToPosition(0);
        }
    }

    /**
     * Called when a fragment will be displayed
     */
    public void willBeDisplayed() {
        // Do what you want here, for example animate the content
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }

    /**
     * Called when a fragment will be hidden
     */
    public void willBeHidden() {
        if (fragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            fragmentContainer.startAnimation(fadeOut);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

/*
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

        Log.d("ObservableScrollView", "onScrollChanged");
        Log.d("scrollY",String.valueOf(scrollY));

//        EventBus.getDefault().post(new BottomNavigationEvent(false));
    }

    @Override
    public void onDownMotionEvent() {

        Log.d("ObservableScrollView", "onDownMotionEvent");
//        EventBus.getDefault().post(new BottomNavigationEvent(false));
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

        if (scrollState == ScrollState.UP) {
            Log.d("scrollState",String.valueOf(scrollState));
            // TODO show or hide the ActionBar
        } else if (scrollState == ScrollState.DOWN) {
            Log.d("scrollState",String.valueOf(scrollState));
            // TODO show or hide the ActionBar
        }
//        EventBus.getDefault().post(new BottomNavigationEvent(true));

    }
*/

}
