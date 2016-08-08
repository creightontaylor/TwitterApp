package com.codepath.apps.mysimpletweets.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.mysimpletweets.Adapter.TweetsRecycleAdapter;
import com.codepath.apps.mysimpletweets.Interface.DismissComposeTweetListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements DismissComposeTweetListener {

    private TwitterClient client;
    private TweetsRecycleAdapter aTweetsAdapter;
    private ArrayList<Tweet> tweets;
    private RecyclerView rvTweets;
    private long maxIDFromLatestTweetFetch;
    private long sinceIDFromLatestTweetFetch = 1;
    private final String SINCE_ID = "since_id";
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    public void setMaxIDFromLatestTweetFetch(Tweet lastTweet) {
        this.maxIDFromLatestTweetFetch = lastTweet.getUnuqueID();
    }

    public void setSinceIDFromLatestTweetFetch(Tweet firstTweet) {
        this.sinceIDFromLatestTweetFetch= firstTweet.getUnuqueID();
    }
    public TimelineActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApplication.getRestClient();
        populateTimeline(SINCE_ID, sinceIDFromLatestTweetFetch);
        setUpRecycleView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.composeTweet) {
            displayComposeFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayComposeFragment() {
        ComposeFragment composeFragment = new ComposeFragment();
        composeFragment.show(getFragmentManager(), "frament_compose");
    }

    private void setUpRecycleView() {
        rvTweets = (RecyclerView) findViewById(R.id.tweetCardRecycleView);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvTweets.setLayoutManager(staggeredGridLayoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline("max_id", maxIDFromLatestTweetFetch);
            }
        });

        tweets = new ArrayList<>();
        aTweetsAdapter = new TweetsRecycleAdapter(tweets);
        rvTweets.setAdapter(aTweetsAdapter);
    }

    private void populateTimeline(final String fetchTag, long sinceID) {
        client.getHomeTimeline(fetchTag, sinceID, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> list = Tweet.fromJSONarray(response);
                setFetchIDs(list);

                if (fetchTag == SINCE_ID) {
                    tweets.addAll(0, list);
                    aTweetsAdapter.notifyItemRangeInserted(0, list.size() - 1);
                    staggeredGridLayoutManager.scrollToPosition(0);
                } else {
                    tweets.addAll(list);
                    aTweetsAdapter.notifyItemRangeInserted(aTweetsAdapter.getItemCount(), tweets.size() - 1);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug", "Failure" + errorResponse.toString());
            }
        });
    }

    private void setFetchIDs(ArrayList<Tweet> list) {
        setMaxIDFromLatestTweetFetch(list.get(list.size() - 1));
        setSinceIDFromLatestTweetFetch(list.get(0));
    }

    public void onCompleteUserInput(String input) {
        client.composeTweet(input, new JsonHttpResponseHandler() {
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
}
