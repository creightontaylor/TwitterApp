package com.codepath.apps.mysimpletweets.Fragment;

import android.util.Log;

import com.codepath.apps.mysimpletweets.Interface.DismissComposeTweetListener;
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
public class ComposeTweetFragment extends TweetsListFragment implements DismissComposeTweetListener{

    public void onCompleteUserInput(String input) {
        TwitterApplication.getRestClient().composeTweet(input, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                populateTimeline(SINCE_ID, sinceIDFromLatestTweetFetch);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "Failure" + errorResponse.toString());
            }
        });
    }

    @Override
    public void userScrolledPastBenchmark() {

    }

    public void populateTimeline(final String fetchTag, long sinceID) {
        TwitterApplication.getRestClient().getMentionsTimeline(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> list = Tweet.fromJSONarray(response);
                addAll(list, null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug", "Failure" + errorResponse.toString());
            }
        });
    }

}
