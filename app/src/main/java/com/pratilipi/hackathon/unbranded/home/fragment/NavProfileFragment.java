package com.pratilipi.hackathon.unbranded.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.pratilipi.hackathon.unbranded.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class NavProfileFragment extends Fragment {

    Unbinder unbinder;


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


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

