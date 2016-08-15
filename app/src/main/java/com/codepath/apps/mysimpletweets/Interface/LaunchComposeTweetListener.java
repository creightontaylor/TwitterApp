package com.codepath.apps.mysimpletweets.Interface;

import com.codepath.apps.mysimpletweets.models.Tweet;

/**
 * Created by andrj148 on 8/7/16.
 */
public interface LaunchComposeTweetListener {
    void onCompletedUserAction(Tweet selectedTweet, String buttonType);
}
