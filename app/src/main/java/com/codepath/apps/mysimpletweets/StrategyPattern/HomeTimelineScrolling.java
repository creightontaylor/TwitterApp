package com.codepath.apps.mysimpletweets.StrategyPattern;

import com.codepath.apps.mysimpletweets.Fragment.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.Fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweets.Interface.InfiniteScrollListener;

/**
 * Created by andrj148 on 8/12/16.
 */
public class HomeTimelineScrolling implements InfiniteScrollListener {
    @Override
    public void userScrolledPastBenchmark(TweetsListFragment tweetsListFragment) {
        HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) tweetsListFragment;
        homeTimelineFragment.populateTimeline("max_id", homeTimelineFragment.maxIDFromLatestTweetFetch);
    }
}
