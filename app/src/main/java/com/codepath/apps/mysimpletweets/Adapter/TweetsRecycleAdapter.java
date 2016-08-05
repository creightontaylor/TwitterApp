package com.codepath.apps.mysimpletweets.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by andrj148 on 8/4/16.
 */
public class TweetsRecycleAdapter extends RecyclerView.Adapter<TweetsRecycleAdapter.ViewHolder> {
    private ArrayList<Tweet> tweets;

    private ImageView ivAvatar;
    private ImageView ivBodyPicture;
    private TextView tvScreenName;
    private TextView tvHandle;
    private TextView tvTimeStamp;
    private TextView tvBody;
    private TextView tvLikes;
    private TextView tvRetweets;
    private Button btnRetweet;
    private Button btnLike;
    private Button btnReply;

    private Context thisContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cv) {
            super(cv);
            cardView = cv;
        }
    }

    public TweetsRecycleAdapter(ArrayList<Tweet> tweetsToDisplay) {
        tweets = tweetsToDisplay;
    }

    @Override
    public TweetsRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        thisContext = parent.getContext();
        CardView cv = (CardView) LayoutInflater.from(thisContext).inflate(R.layout.card_item_tweet, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        Tweet tweet = tweets.get(position);
        findAndSetupSubviews(cardView, tweet);
    }

    private void findAndSetupSubviews(CardView cv, Tweet tweet) {
        ivAvatar = (ImageView) cv.findViewById(R.id.ivProfileImage);
        if (ivAvatar != null) {
            setImageFor(ivAvatar, tweet);
        }
        ivBodyPicture = (ImageView) cv.findViewById(R.id.ivBodyPicture);
        if (ivBodyPicture != null) {
            setImageFor(ivBodyPicture, tweet);
        }

        tvScreenName = (TextView) cv.findViewById(R.id.tvScreenName);
        tvScreenName.setText(tweet.getUser().getScreeName());

        tvHandle = (TextView) cv.findViewById(R.id.tvHandle);
        tvHandle.setText(tweet.getUser().getName());

        tvTimeStamp = (TextView) cv.findViewById(R.id.tvTimeStamp);
        tvTimeStamp.setText(formatTimestamp(tweet.getCreatedAt()));

        tvBody = (TextView) cv.findViewById(R.id.tvBody);
        tvBody.setText(tweet.getBody());

        tvLikes = (TextView) cv.findViewById(R.id.tvLikes);
        tvLikes.setText(String.valueOf(tweet.getRetweets()));

        tvRetweets = (TextView) cv.findViewById(R.id.tvRetweets);
        tvRetweets.setText(String.valueOf(tweet.getRetweets()));

        btnRetweet = (Button) cv.findViewById(R.id.btnRetweet);
        btnRetweet.setOnClickListener(retweetHandler);
        btnLike = (Button) cv.findViewById(R.id.btnLike);
        btnLike.setOnClickListener(likeHandler);
        btnReply = (Button) cv.findViewById(R.id.btnReply);
        btnReply.setOnClickListener(replyHandler);
    }

    View.OnClickListener retweetHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(thisContext, "Retweet button tapped", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener likeHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(thisContext, "Like button tapped", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener replyHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(thisContext, "Reply button tapped", Toast.LENGTH_SHORT).show();
        }
    };

    private String formatTimestamp(String originalTimeStamp) {
        String formattedTimestamp = "";

        return formattedTimestamp;
    }

    private void setImageFor(ImageView imageView, Tweet tweet) {
        imageView.setImageResource(0);
        if (!TextUtils.isEmpty(tweet.getUser().getProfileImageURL())) {
            Picasso.with(thisContext).load(tweet.getUser().getProfileImageURL()).into(imageView);
        }
    }
}
