package com.codepath.apps.mysimpletweets.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.StrategyPattern.MentionsTimelineRefresh;
import com.codepath.apps.mysimpletweets.StrategyPattern.MentionsTimelineScrolling;
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
public class MentionsTimelineFragment extends TweetsListFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateMentionsTimeline(SINCE_ID, sinceIDFromLatestTweetFetch);
        setStrategyPatternTypes();
    }

    private void setStrategyPatternTypes() {
        infiniteScrollListenerType = new MentionsTimelineScrolling();
        refreshTweetsTimelineType = new MentionsTimelineRefresh();
    }

        public void populateMentionsTimeline(final String fetchTag, long sinceID) {
        TwitterApplication.getRestClient().getMentionsTimeline(fetchTag, sinceID, new JsonHttpResponseHandler() {
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
