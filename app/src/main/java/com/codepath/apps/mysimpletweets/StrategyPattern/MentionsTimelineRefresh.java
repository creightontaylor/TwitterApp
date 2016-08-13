package com.codepath.apps.mysimpletweets.StrategyPattern;

import com.codepath.apps.mysimpletweets.Fragment.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.Fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweets.Interface.RefreshTweetsTimeline;

/**
 * Created by andrj148 on 8/13/16.
 */
public class MentionsTimelineRefresh implements RefreshTweetsTimeline {
    @Override
    public void refreshTimeLineAndNavigateToTopTweet(TweetsListFragment tweetsListFragment) {
        MentionsTimelineFragment mentionsTimelineFragment = (MentionsTimelineFragment) tweetsListFragment;
        mentionsTimelineFragment.populateMentionsTimeline();
    }
}
