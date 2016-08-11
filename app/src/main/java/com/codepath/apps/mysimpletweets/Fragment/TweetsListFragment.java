package com.codepath.apps.mysimpletweets.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.Activity.EndlessRecyclerViewScrollListener;
import com.codepath.apps.mysimpletweets.Adapter.TweetsRecycleAdapter;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;

/**
 * Created by andrj148 on 8/11/16.
 */
public class TweetsListFragment extends Fragment {
    private TweetsRecycleAdapter aTweetsAdapter;
    private ArrayList<Tweet> tweets;
    private RecyclerView rvTweets;
    private long maxIDFromLatestTweetFetch;
    private long sinceIDFromLatestTweetFetch = 1;
    private final String SINCE_ID = "since_id";
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets, container, false);
        setUpRecycleView();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupCollection();
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
    }

    private void setupCollection() {
        tweets = new ArrayList<>();
        aTweetsAdapter = new TweetsRecycleAdapter(tweets);
        rvTweets.setAdapter(aTweetsAdapter);
    }

    public void addAll (ArrayList<Tweet> list, String fetchTag) {
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

    private void setFetchIDs(ArrayList<Tweet> list) {
        setMaxIDFromLatestTweetFetch(list.get(list.size() - 1));
        setSinceIDFromLatestTweetFetch(list.get(0));
    }

    public void setMaxIDFromLatestTweetFetch(Tweet lastTweet) {
        this.maxIDFromLatestTweetFetch = lastTweet.getUnuqueID();
    }

    public void setSinceIDFromLatestTweetFetch(Tweet firstTweet) {
        this.sinceIDFromLatestTweetFetch= firstTweet.getUnuqueID();
    }
}
