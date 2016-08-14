package com.codepath.apps.mysimpletweets.models;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by andrj148 on 8/4/16.
 */
@Parcel
public class User {
    private String name;
    private Long uid;
    private String screeName;
    private String profileImageURL;
    private String location;
    private int followers;
    private int following;
    private String description;
    private String backgroundImageURL;


    public String getDescription() {return description;}

    public String getName() {
        return name;
    }

    public Long getUid() {
        return uid;
    }

    public String getScreeName() {
        return screeName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public int getFollowers() {return followers; }

    public int getFollowing() {return following;}

    public String getLocation() {return location;  }

    public String getBackgroundImageURL() {return backgroundImageURL;}

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screeName = jsonObject.getString("screen_name");
            user.profileImageURL = jsonObject.getString("profile_image_url");
            user.location = jsonObject.getString("location");
            user.followers = jsonObject.getInt("followers_count");
            user.following = jsonObject.getInt("following");
            user.description = jsonObject.getString("text");
            user.backgroundImageURL = jsonObject.getString("profile_background_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void setProfileImageWithRoundedCorners(Context context, String profileURL, ImageView ivProfileImage) {
        Picasso.with(context).load(profileURL).transform(new RoundedCornersTransformation(10, 0)).into(ivProfileImage);

    }
}
