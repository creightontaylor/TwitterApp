package com.codepath.apps.mysimpletweets.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.StrategyPattern.HomeTimelineRefresh;
import com.codepath.apps.mysimpletweets.StrategyPattern.HomeTimelineScrolling;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andrj148 on 8/12/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTimeline(SINCE_ID, sinceIDFromLatestTweetFetch);
        setStrategyPatternTypes();
     }

    private void setStrategyPatternTypes() {
        infiniteScrollListenerType = new HomeTimelineScrolling();
        refreshTweetsTimelineType = new HomeTimelineRefresh();
    }

    public void populateTimeline(final String fetchTag, long sinceID) {
        TwitterApplication.getRestClient().getHomeTimeline(fetchTag, sinceID, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> list = Tweet.fromJSONarray(response);
                setFetchIDs(list);
                addAll(list, fetchTag);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug", "Failure" + errorResponse.toString());
            }
        });
    }
}
