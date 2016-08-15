package com.codepath.apps.mysimpletweets.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.Fragment.ComposeDialogFragment;
import com.codepath.apps.mysimpletweets.Fragment.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.Fragment.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.Fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweets.Interface.DismissComposeTweetListener;
import com.codepath.apps.mysimpletweets.Interface.LaunchComposeTweetListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements LaunchComposeTweetListener, DismissComposeTweetListener {
    private TweetsPagerAdapter tweetsPagerAdapter;
    public int currentFragmentPosition = 0;
    private User user;
    public TimelineActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        setupViewPager();
        getUserInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.composeTweet) {
            displayComposeFragmentFromAction(null, null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager() {
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        tweetsPagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(tweetsPagerAdapter);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);

    }

    private void displayComposeFragmentFromAction(Tweet selectedTweet, String buttonType) {
        ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance(selectedTweet, user, buttonType);
        composeDialogFragment.show(getFragmentManager(), "fragment_compose_from_action");
    }

    private void getUserInfo() {
        TwitterApplication.getRestClient().getUserInfo(null ,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG", "getting User Info FAILED" + errorResponse.toString());
            }
        });
    }

    @Override
    public void onCompletedUserAction(Tweet selectedTweet, String buttonType) {
        displayComposeFragmentFromAction(selectedTweet, buttonType);
    }

    @Override
    public void onCompleteUserInput(String input) {
        TwitterApplication.getRestClient().composeTweet(input, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                TweetsListFragment currentFragment = (TweetsListFragment) tweetsPagerAdapter.getItem(currentFragmentPosition);
                currentFragment.refreshTweetsTimelineType.refreshTimeLineAndNavigateToTopTweet(currentFragment);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "Compose Tweet Failure" + errorResponse.toString());
            }
        });
    }

    public void onProfileView(MenuItem item) {
        Intent navigateToUserProfile = new Intent(this, ProfileActivity.class);
        navigateToUserProfile.putExtra("user", Parcels.wrap(user));
//        startActivity(navigateToUserProfile);
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = {"Home", "Mentions"};
        private Context currentContext;

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1){
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
