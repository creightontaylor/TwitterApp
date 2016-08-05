package com.codepath.apps.mysimpletweets.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by andrj148 on 8/4/16.
 */
public class TweetsRecycleAdapter extends RecyclerView.Adapter<TweetsRecycleAdapter.ViewHolder> {
    private ArrayList<Tweet> tweets;
    private Context thisContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

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

        public ViewHolder(CardView cv) {
            super(cv);
            findSubViews(cv);
        }

        public void findSubViews(CardView cv) {
            ivAvatar = (ImageView) cv.findViewById(R.id.ivProfileImage);
            ivBodyPicture = (ImageView) cv.findViewById(R.id.ivBodyPicture);

            tvScreenName = (TextView) cv.findViewById(R.id.tvScreenName);
            tvHandle = (TextView) cv.findViewById(R.id.tvHandle);
            tvTimeStamp = (TextView) cv.findViewById(R.id.tvTimeStamp);
            tvBody = (TextView) cv.findViewById(R.id.tvBody);
            tvLikes = (TextView) cv.findViewById(R.id.tvLikes);
            tvRetweets = (TextView) cv.findViewById(R.id.tvRetweets);

            btnRetweet = (Button) cv.findViewById(R.id.btnRetweet);
            btnLike = (Button) cv.findViewById(R.id.btnLike);
            btnReply = (Button) cv.findViewById(R.id.btnReply);
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
        Tweet tweet = tweets.get(position);
        setSubviews(holder, tweet);
    }

    private void setSubviews(ViewHolder viewHolder, Tweet tweet) {
        if (viewHolder.ivAvatar != null) {
            setImageFor(viewHolder.ivAvatar, tweet);
        }

        if (viewHolder.ivBodyPicture != null) {
            setImageFor(viewHolder.ivBodyPicture, tweet);
        }

        viewHolder.tvScreenName.setText(tweet.getUser().getScreeName());
        viewHolder.tvHandle.setText(tweet.getUser().getName());
        viewHolder.tvTimeStamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.tvLikes.setText(String.valueOf(tweet.getRetweets()));
        viewHolder.tvRetweets.setText(String.valueOf(tweet.getRetweets()));

        viewHolder.btnRetweet.setOnClickListener(retweetHandler);
        viewHolder.btnLike.setOnClickListener(likeHandler);
        viewHolder.btnReply.setOnClickListener(replyHandler);
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

    private String getRelativeTimeAgo(String originalTimeStamp) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(originalTimeStamp).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    private void setImageFor(ImageView imageView, Tweet tweet) {
        imageView.setImageResource(0);
        if (!TextUtils.isEmpty(tweet.getUser().getProfileImageURL())) {
            Picasso.with(thisContext).load(tweet.getUser().getProfileImageURL()).into(imageView);
        }
    }
}
