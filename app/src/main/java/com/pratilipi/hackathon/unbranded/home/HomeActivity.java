package com.pratilipi.hackathon.unbranded.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.base.BaseActivity;
import com.pratilipi.hackathon.unbranded.eventBus.BottomNavigationEvent;
import com.pratilipi.hackathon.unbranded.home.fragment.HomeFragmentViewPagerAdapter;
import com.pratilipi.hackathon.unbranded.home.fragment.NavCreateFragment;
import com.pratilipi.hackathon.unbranded.home.fragment.NavProfileFragment;
import com.pratilipi.hackathon.unbranded.utils.NotificationChannelHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

//    @BindView(R.id.activity_main)
//    LinearLayout activityMain;

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    Activity activity = this;
    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;

    private Fragment currentFragment;
    private HomeFragmentViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUnBinder(ButterKnife.bind(this));

        setUp();

    }

    @Override
    protected void setUp() {

        setToolbar();
        setupBottomNavigation();

    }

    private void setupBottomNavigation() {
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_home_black_24dp, R.color.white);
//        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_copy_white_24dp, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_add_white_24px, R.color.white);
//        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.ic_add_white_24px, R.color.white);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.tab_5, R.drawable.ic_info_24px, R.color.white);

// Add items
        bottomNavigation.addItem(item1);
//        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
//        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);


// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

//          bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        bottomNavigation.setBehaviorTranslationEnabled(true);

// Use colored navigation with circle reveal effect
        bottomNavigation.setColored(false);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

//                currentFragment = adapter.getCurrentFragment();
                //adapter.setCurrentFragment(position);
                viewPager.setCurrentItem(position);

                try {
                    if (position == 2) {
                        Fragment fragment = adapter.getItem(position);
                        if (fragment instanceof NavProfileFragment) {
                            ((NavProfileFragment) fragment).onPageRefresh();
                        }

                    }else if (position == 1) {
                        Fragment fragment = adapter.getItem(position);
                        if (fragment instanceof NavCreateFragment) {
                            ((NavCreateFragment) fragment).onPageRefresh();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return true;
            }
        });

        viewPager.setOffscreenPageLimit(3);
        adapter = new HomeFragmentViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

//        currentFragment = adapter.getCurrentFragment();

        try {
            NotificationChannelHelper.createChannels(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showOrHideBottomNavigation(boolean show) {
        if (show) {
            bottomNavigation.restoreBottomNavigation(true);
        } else {
            bottomNavigation.hideBottomNavigation(true);
        }
    }


    private void setToolbar() {

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BottomNavigationEvent event) {

        Log.d("onEvent", String.valueOf(event.showMenu()));

        showOrHideBottomNavigation(event.showMenu());


    }

}
