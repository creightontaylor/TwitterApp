package com.codepath.apps.mysimpletweets.StrategyPattern;

import com.codepath.apps.mysimpletweets.Fragment.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.Fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweets.Interface.RefreshTweetsTimeline;

/**
 * Created by andrj148 on 8/13/16.
 */
public class HomeTimelineRefresh implements RefreshTweetsTimeline {

    @Override
    public void refreshTimeLineAndNavigateToTopTweet(TweetsListFragment tweetsListFragment) {
        HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) tweetsListFragment;
        homeTimelineFragment.populateTimeline(homeTimelineFragment.SINCE_ID, homeTimelineFragment.sinceIDFromLatestTweetFetch);
    }
}
