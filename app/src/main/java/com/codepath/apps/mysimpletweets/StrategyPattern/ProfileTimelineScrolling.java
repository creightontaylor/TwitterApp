package com.codepath.apps.mysimpletweets.StrategyPattern;

import com.codepath.apps.mysimpletweets.Fragment.ProfileTimelineFragment;
import com.codepath.apps.mysimpletweets.Fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweets.Interface.InfiniteScrollListener;

/**
 * Created by andrj148 on 8/14/16.
 */
public class ProfileTimelineScrolling implements InfiniteScrollListener {
    @Override
    public void userScrolledPastBenchmark(TweetsListFragment tweetsListFragment) {
        ProfileTimelineFragment profileTimelineFragment = (ProfileTimelineFragment) tweetsListFragment;
        profileTimelineFragment.populateUserTimeline(profileTimelineFragment.screenName, profileTimelineFragment.SINCE_ID, profileTimelineFragment.sinceIDFromLatestTweetFetch);
    }
}
