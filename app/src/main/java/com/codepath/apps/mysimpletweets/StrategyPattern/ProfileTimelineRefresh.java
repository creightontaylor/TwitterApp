package com.codepath.apps.mysimpletweets.StrategyPattern;

import com.codepath.apps.mysimpletweets.Fragment.ProfileTimelineFragment;
import com.codepath.apps.mysimpletweets.Fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweets.Interface.RefreshTweetsTimeline;

/**
 * Created by andrj148 on 8/14/16.
 */
public class ProfileTimelineRefresh implements RefreshTweetsTimeline {
    @Override
    public void refreshTimeLineAndNavigateToTopTweet(TweetsListFragment tweetsListFragment) {
        ProfileTimelineFragment profileTimelineFragment = (ProfileTimelineFragment) tweetsListFragment;
        profileTimelineFragment.populateUserTimeline(profileTimelineFragment.screenName, profileTimelineFragment.SINCE_ID, profileTimelineFragment.sinceIDFromLatestTweetFetch);
    }
}
