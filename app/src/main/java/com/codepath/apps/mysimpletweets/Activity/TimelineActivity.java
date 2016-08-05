package com.codepath.apps.mysimpletweets.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.codepath.apps.mysimpletweets.Adapter.TweetsRecycleAdapter;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private TweetsRecycleAdapter aTweetsAdapter;
    private ArrayList<Tweet> tweets;
    private RecyclerView rvTweets;

    public TimelineActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApplication.getRestClient();
        populateTimeline(1);
        setUpRecycleView();
    }

    private void setUpRecycleView() {
        rvTweets = (RecyclerView) findViewById(R.id.tweetCardRecycleView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvTweets.setLayoutManager(staggeredGridLayoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(page);
            }
        });

        tweets = new ArrayList<>();
        aTweetsAdapter = new TweetsRecycleAdapter(tweets);
        rvTweets.setAdapter(aTweetsAdapter);
    }

    private void populateTimeline(int sinceID) {
        client.getHomeTimeline(sinceID, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                tweets.clear();
                tweets.addAll(Tweet.fromJSONarray(response));
                aTweetsAdapter.notifyDataSetChanged();
                Log.d("debug", "Adapter Array contents" + aTweetsAdapter.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug", "Failure" + errorResponse.toString());
            }
        });
    }
}
