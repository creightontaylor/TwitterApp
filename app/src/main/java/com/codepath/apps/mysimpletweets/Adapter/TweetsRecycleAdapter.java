package com.codepath.apps.mysimpletweets.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.Interface.LaunchComposeTweetListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by andrj148 on 8/4/16.
 */
public class TweetsRecycleAdapter extends RecyclerView.Adapter<TweetsRecycleAdapter.ViewHolder> {
    private ArrayList<Tweet> tweets;
    private Context thisContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
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
            cardView = cv;
            findSubViews(cv);
        }
//should I pass array of tweets into viewholder instead of adapter?
        public void findSubViews(CardView cv) {
            ivAvatar = (ImageView) cv.findViewById(R.id.ivAvatar);
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
        Log.d("DEBUG", "position =" + String.valueOf(position) + "for tweet =" + tweet.getBody());
        setSubviews(holder, tweet);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(thisContext, "Tweet Cardview tapped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSubviews(ViewHolder viewHolder, final Tweet tweet) {
        ImageView ivAvatar = viewHolder.ivAvatar;
        ivAvatar.setImageResource(0);
        if (tweet.getUser().getProfileImageURL() != null) {
            setImageFor(ivAvatar, tweet.getUser().getProfileImageURL());
        }

        ImageView ivBodyImage = viewHolder.ivBodyPicture;
        ivBodyImage.setImageResource(0);
        if (tweet.getBodyImageURL() != null) {
            setImageFor(ivBodyImage, tweet.getBodyImageURL());
        }

        TextView name = viewHolder.tvScreenName;
        name.setText("@" + tweet.getUser().getScreeName());

        TextView handle = viewHolder.tvHandle;
        handle.setText(tweet.getUser().getName());

        TextView timeStamp = viewHolder.tvTimeStamp;
        timeStamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        TextView body = viewHolder.tvBody;
        body.setText(tweet.getBody());

        TextView likes = viewHolder.tvLikes;
        likes.setText(String.valueOf(tweet.getLikes()));

        TextView reTweets = viewHolder.tvRetweets;
        reTweets.setText(String.valueOf(tweet.getRetweets()));

        Button reTweetButton = viewHolder.btnRetweet;
        reTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(thisContext, "Retweet button tapped", Toast.LENGTH_SHORT).show();
                LaunchComposeTweetListener listener = (LaunchComposeTweetListener) thisContext;
                listener.onCompletedUserAction(tweet);
            }
        });

        Button likeButton = viewHolder.btnLike;
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(thisContext, "Like button tapped", Toast.LENGTH_SHORT).show();

            }
        });

        Button replyButton = viewHolder.btnReply;
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(thisContext, "Reply button tapped", Toast.LENGTH_SHORT).show();
                LaunchComposeTweetListener listener = (LaunchComposeTweetListener) thisContext;
                listener.onCompletedUserAction(tweet);
            }
        });
    }


    private String getRelativeTimeAgo(String originalTimeStamp) {

        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(originalTimeStamp).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    private void setImageFor(ImageView imageView, String url) {
        Picasso.with(thisContext).load(url).transform(new RoundedCornersTransformation(10, 0)).into(imageView);
    }
}
