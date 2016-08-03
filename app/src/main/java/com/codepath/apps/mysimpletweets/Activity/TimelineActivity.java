package com.codepath.apps.mysimpletweets.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void populateTimeline() {

    }
}
