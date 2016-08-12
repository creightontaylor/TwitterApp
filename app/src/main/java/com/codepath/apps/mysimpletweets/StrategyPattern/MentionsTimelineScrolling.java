package com.codepath.apps.mysimpletweets.StrategyPattern;

import com.codepath.apps.mysimpletweets.Fragment.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.Fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweets.Interface.InfiniteScrollListener;

/**
 * Created by andrj148 on 8/12/16.
 */
public class MentionsTimelineScrolling implements InfiniteScrollListener {
    @Override
    public void userScrolledPastBenchmark(TweetsListFragment tweetsListFragment) {
        MentionsTimelineFragment mentionsTimelineFragment = (MentionsTimelineFragment) tweetsListFragment;

    }
}
