package com.codepath.apps.mysimpletweets.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.mysimpletweets.Fragment.ComposeDialogFragment;
import com.codepath.apps.mysimpletweets.Fragment.TweetsListFragment;
import com.codepath.apps.mysimpletweets.Interface.DismissComposeTweetListener;
import com.codepath.apps.mysimpletweets.Interface.LaunchComposeTweetListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;

public class TimelineActivity extends AppCompatActivity implements LaunchComposeTweetListener, DismissComposeTweetListener {


    public TimelineActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
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
        ComposeDialogFragment composeDialogFragment = new ComposeDialogFragment();
        composeDialogFragment.show(getFragmentManager(), "fragment_compose");
    }
    private void displayComposeFragmentFromAction(Tweet selectedTweet) {
        ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance(selectedTweet);
        composeDialogFragment.show(getFragmentManager(), "fragment_compose_from_action");
    }

    @Override
    public void onCompletedUserAction(Tweet selectedTweet) {
        displayComposeFragmentFromAction(selectedTweet);
    }

    @Override
    public void onCompleteUserInput(String input) {
        //navigate to current fragment and show top
        TweetsListFragment fragmentContainer = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        if (fragmentContainer != null) {
            fragmentContainer.refreshTweetsTimelineType.refreshTimeLineAndNavigateToTopTweet(fragmentContainer);
        } else {

        }

    }
}
