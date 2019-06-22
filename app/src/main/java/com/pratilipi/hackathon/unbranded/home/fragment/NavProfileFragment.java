package com.pratilipi.hackathon.unbranded.home.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.pratilipi.hackathon.unbranded.LogInActivity;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.home.adapter.ProfilePagerTabsAdapter;
import com.pratilipi.hackathon.unbranded.network.ApiEndPoint;
import com.pratilipi.hackathon.unbranded.network.model.User;
import com.pratilipi.hackathon.unbranded.network.model.UserProduct;
import com.pratilipi.hackathon.unbranded.profile.ProfileProductsFragment;
import com.pratilipi.hackathon.unbranded.rxjava.AppSchedulerProvider;
import com.pratilipi.hackathon.unbranded.utils.AppUtils;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 *
 */
public class NavProfileFragment extends Fragment {

    private final String TAG = NavProfileFragment.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.img_user)
    AppCompatImageView imgUser;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.img_sign_out)
    AppCompatImageView imgSignOut;
    @BindView(R.id.user_email)
    TextView userEmail;
    @BindView(R.id.user_followers)
    TextView userFollowers;
    @BindView(R.id.tl_tabs)
    TabLayout tlTabs;
    @BindView(R.id.vp_profile)
    ViewPager vpProfile;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    String titles[] = {"My Products", "My Followers"};
    ProfilePagerTabsAdapter profileAdapter;
    private CompositeDisposable mCompositeDisposable;
    private FirebaseAuth mAuth;

    public static NavProfileFragment newInstance(int index) {
        NavProfileFragment fragment = new NavProfileFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_profile, container, false);

        unbinder = ButterKnife.bind(this, view);

        mCompositeDisposable = new CompositeDisposable();
        profileAdapter = new ProfilePagerTabsAdapter(getChildFragmentManager(), getActivity(), titles);
        vpProfile.setOffscreenPageLimit(3);
        vpProfile.setAdapter(profileAdapter);
        tlTabs.setupWithViewPager(vpProfile);
//        recyclerTabLayout.setUpWithViewPager(vpHome);

        mAuth = FirebaseAuth.getInstance();
        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            // send unfollow request
                                            mAuth.signOut();
                                            startLoginActivity();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                builder.setMessage("Do you really wan't t0 sign out ?");

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(ContextCompat.getColor(getContext(), R.color.trans_black_40));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(ContextCompat.getColor(getContext(), R.color.red_500));
            }
        });

        vpProfile.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                vpProfile.setCurrentItem(position);
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

        try {
            getUserDetailsServer(AppUtils.getUserId(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;

    }

    private void startLoginActivity() {
        Intent intent = new Intent(getContext(), LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void getUserDetailsServer(String userId) {
        Log.d(TAG, "getUserDetailsServer: ");
        mCompositeDisposable.add(getUserDsta(userId)
                .subscribeOn(new AppSchedulerProvider().io())
                .observeOn(new AppSchedulerProvider().ui())
                .subscribe(new Consumer<UserProduct>() {
                    @Override
                    public void accept(UserProduct userProduct) throws Exception {
                        if (userProduct != null) {
                            setUpUi(userProduct);
                        }


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                    hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            Log.d("anError ", anError.toString());
//                            handleApiError(anError);
                        }
                    }
                }));
    }

    private void setUpUi(UserProduct userProduct) {
        try {
            User user = userProduct.getUserList().get(0);
            userName.setText(user.getName());
            userEmail.setText(user.getEmail());

            userFollowers.setText(String.format(Locale.getDefault(),
                    "%d Followers", user.getFollowers()));

            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_def_user_circle_128)
                    .error(R.drawable.ic_def_user_circle_128)
                    .priority(Priority.HIGH)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);


            if (user.getImageUrl() != null) {
                Glide.with(getContext())
                        .load(user.getImageUrl())
                        .apply(options)
                        .into(imgUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private Observable<UserProduct> getUserDsta(String userId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_USER)
//                .addHeaders("user-Id", "3")
                .addQueryParameter("id", AppUtils.getUserId(getContext()))
                .build()
                .getObjectObservable(UserProduct.class);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.dispose();
        unbinder.unbind();
    }

    public void onPageRefresh() {
        if (profileAdapter != null) {
            try {

                Fragment fragment = profileAdapter.getItem(0);
                if (fragment instanceof ProfileProductsFragment) {
                    ((ProfileProductsFragment) fragment).onPageRefresh();
                }
                try {
                    getUserDetailsServer(AppUtils.getUserId(getContext()));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

