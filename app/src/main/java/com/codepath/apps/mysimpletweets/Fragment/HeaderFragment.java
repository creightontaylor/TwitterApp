package com.codepath.apps.mysimpletweets.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

/**
 * Created by andrj148 on 8/13/16.
 */
public class HeaderFragment extends Fragment {
    User user;

    public static HeaderFragment newInstance(User currentUser) {
        HeaderFragment fragmentHeader = new HeaderFragment();
        fragmentHeader.user = currentUser;

        return fragmentHeader;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_header, container, false);
        populateProfileHeader(v);
        return v;
    }

    private void populateProfileHeader(View view) {
        TextView tvScreenName = (TextView) view.findViewById(R.id.tvScreenName);
        tvScreenName.setText(user.getScreeName());

        TextView tvHandle = (TextView) view.findViewById(R.id.tvHandle);
        tvHandle.setText(user.getName());

        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        tvDescription.setText(user.getDescription());

        TextView tvFollowers = (TextView) view.findViewById(R.id.tvFollowers);
        String followersDisplay = String.valueOf(user.getFollowers()) + "Followers";
        tvFollowers.setText(followersDisplay);

        TextView tvFollowing = (TextView) view.findViewById(R.id.tvFollowing);
        String followingDisplay = String.valueOf(user.getFollowers()) + "Following";
        tvFollowing.setText(followingDisplay);

        if (user.getBackgroundImageURL() != null) {
            ImageView ivBackgroundImage = (ImageView) view.findViewById(R.id.ivBackgroundImage);
            Picasso.with(getActivity()).load(user.getBackgroundImageURL()).into(ivBackgroundImage);
        }

        if (user.getProfileImageURL() != null) {
            ImageView ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
            User.setProfileImageWithRoundedCorners(getActivity(), user.getProfileImageURL(), ivProfileImage);
        }
    }


}
