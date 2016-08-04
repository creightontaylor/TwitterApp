package com.codepath.apps.mysimpletweets.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by andrj148 on 8/4/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        findAndSetupSubviews(convertView, tweet);

       return convertView;
    }

    private void findAndSetupSubviews(View view, Tweet tweet) {
        ImageView ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);

        tvUserName.setText(tweet.getUser().getScreeName());
        tvBody.setText(tweet.getBody());

        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso .with(getContext()).load(tweet.getUser().getProfileImageURL()).into(ivProfileImage);
    }
}
