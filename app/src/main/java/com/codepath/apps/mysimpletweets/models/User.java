package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrj148 on 8/4/16.
 */
public class User {
    private String name;
    private Long uid;
    private String screeName;
    private String profileImageURL;


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


    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screeName = jsonObject.getString("screen_name");
            user.profileImageURL = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

}
