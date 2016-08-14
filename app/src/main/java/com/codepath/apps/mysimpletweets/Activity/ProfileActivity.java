package com.codepath.apps.mysimpletweets.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.mysimpletweets.Fragment.HeaderFragment;
import com.codepath.apps.mysimpletweets.Fragment.ProfileTimelineFragment;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setActionBarToScreenName();
        String screenName = getIntent().getStringExtra("screen_name");

        if (savedInstanceState == null) {
            ProfileTimelineFragment userTimelineFragment = ProfileTimelineFragment.newInstance(screenName);
            fillFrameLayoutWithFragment(userTimelineFragment, R.id.fragment_user_timeline);

            fillFrameLayoutWithFragment(new HeaderFragment(), R.id.fragment_header); //do this statically?
        }
    }

    private void fillFrameLayoutWithFragment(Fragment fragment, int fragmentID) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(fragmentID, fragment);
        ft.commit();
    }

    private void setActionBarToScreenName() {
        TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                getSupportActionBar().setTitle("@" + user.getScreeName());
            }
        });
    }
}
