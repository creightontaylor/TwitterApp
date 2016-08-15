package com.codepath.apps.mysimpletweets.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.codepath.apps.mysimpletweets.Fragment.HeaderFragment;
import com.codepath.apps.mysimpletweets.Fragment.ProfileTimelineFragment;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.User;

import org.parceler.Parcels;

public class ProfileActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User currentUser = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        if (currentUser.getScreeName() != null) {
            setActionBarToScreenName(currentUser.getScreeName());
        }

        if (savedInstanceState == null) {
            ProfileTimelineFragment userTimelineFragment = ProfileTimelineFragment.newInstance(currentUser.getScreeName());
            fillFrameLayoutWithFragment(userTimelineFragment, R.id.fragment_user_timeline);

            HeaderFragment headerFragment = HeaderFragment.newInstance(currentUser);
            fillFrameLayoutWithFragment(headerFragment, R.id.fragment_header_container);
        }
    }

    private void fillFrameLayoutWithFragment(Fragment fragment, int fragmentID) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(fragmentID, fragment);
        ft.commit();
    }

    private void setActionBarToScreenName(String screenName) {
//        getSupportActionBar().setTitle("@" + screenName);
    }


}
