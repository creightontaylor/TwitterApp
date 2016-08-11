package com.codepath.apps.mysimpletweets.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.mysimpletweets.Adapter.TweetsRecycleAdapter;
import com.codepath.apps.mysimpletweets.Fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweets.Interface.DismissComposeTweetListener;
import com.codepath.apps.mysimpletweets.Interface.LaunchComposeTweetListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements DismissComposeTweetListener, LaunchComposeTweetListener {

    private TwitterClient client;
    private TweetsListFragment fragmentTweetsList;



    public TimelineActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApplication.getRestClient();
        populateTimeline(SINCE_ID, sinceIDFromLatestTweetFetch);
        setUpRecycleView();

        if (savedInstanceState ==null) {
            fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        }
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
        composeFragment.show(getFragmentManager(), "fragment_compose");
    }
    private void displayComposeFragmentFromAction(Tweet selectedTweet) {
        ComposeFragment composeFragment = ComposeFragment.newInstance(selectedTweet);
        composeFragment.show(getFragmentManager(), "fragment_compose_from_action");
    }




    private void populateTimeline(final String fetchTag, long sinceID) {
        client.getHomeTimeline(fetchTag, sinceID, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                setFetchIDs(list);
//
//                if (fetchTag == SINCE_ID) {
//                    tweets.addAll(0, list);
//                    aTweetsAdapter.notifyItemRangeInserted(0, list.size() - 1);
//                    staggeredGridLayoutManager.scrollToPosition(0);
//                } else {
//                    tweets.addAll(list);
//                    aTweetsAdapter.notifyItemRangeInserted(aTweetsAdapter.getItemCount(), tweets.size() - 1);
//                }
                fragmentTweetsList.addAll(Tweet.fromJSONarray(response), fetchTag);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug", "Failure" + errorResponse.toString());
            }
        });
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

    @Override
    public void onCompletedUserAction(Tweet selectedTweet) {
        displayComposeFragmentFromAction(selectedTweet);
    }
}
