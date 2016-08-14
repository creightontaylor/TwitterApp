package com.codepath.apps.mysimpletweets.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andrj148 on 8/13/16.
 */
public class ProfileTimelineFragment extends TweetsListFragment {

    public static ProfileTimelineFragment newInstance(String screen_name) {
        ProfileTimelineFragment userTimelineFragment = new ProfileTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userTimelineFragment.setArguments(args);

        return userTimelineFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String screenName = getArguments().getString("screen_name");
        populateUserTimeline(screenName, SINCE_ID, sinceIDFromLatestTweetFetch);
        setStrategyPatternTypes();
    }

    private void setStrategyPatternTypes() {
//        infiniteScrollListenerType = new UserTimelineScrolling();
//        refreshTweetsTimelineType = new UserTimelineRefresh();
    }

    public void populateUserTimeline(String screenName, final String fetchTag, long sinceID) {
        TwitterApplication.getRestClient().getUserTimeline(screenName, fetchTag, sinceID, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                ArrayList<Tweet> list = Tweet.fromJSONarray(json);
                setFetchIDs(list);
                addAll(list, fetchTag);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
