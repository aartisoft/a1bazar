package com.pratilipi.hackathon.unbranded.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.home.fragment.NavProfileFragment;
import com.pratilipi.hackathon.unbranded.network.model.User;
import com.pratilipi.hackathon.unbranded.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.frame_)
    FrameLayout frame;

    private NavProfileFragment navProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        User user = (User) getIntent().getSerializableExtra(AppConstants.EXTRA_USER);

        navProfileFragment = NavProfileFragment.newInstance(user);
//        navProfileFragment.setListener(this);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_, navProfileFragment,
                        navProfileFragment.getClass().getSimpleName())
                .commit();


    }
}
