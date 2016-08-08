package com.codepath.apps.mysimpletweets.Activity;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.Interface.DismissComposeTweetListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ComposeFragment extends DialogFragment {
    private EditText etComposeBody;
    private User currentUser;
    private TextView tvCharacterCount;

    public ComposeFragment() {
        // Required empty public constructor
    }

    public static ComposeFragment newInstance(Tweet selectedTweet) {
        ComposeFragment frag = new ComposeFragment();
        Bundle args = new Bundle();
        args.putParcelable("selectedTweet", Parcels.wrap(selectedTweet));
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etComposeBody = (EditText) view.findViewById(R.id.etComposeBody);
        showKeyboardAndFocusOnBody(etComposeBody);

        tvCharacterCount = (TextView) view.findViewById(R.id.tvCharacterCount);

        Tweet selectedTweet = (Tweet) Parcels.unwrap(getArguments().getParcelable("selectedTweet"));
        if (selectedTweet != null) {
            setupRetweet(view, selectedTweet);
        }

        Button btnTweet = (Button) view.findViewById(R.id.btnTweet);
        setupTweetButtonPressed(btnTweet);

        Button btnDismiss = (Button) view.findViewById(R.id.btnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void setupRetweet(View view, Tweet selectedTweet) {
        etComposeBody.setText(selectedTweet.getBody());

        ImageView ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
        Picasso.with(getActivity()).load(selectedTweet.getUser().getProfileImageURL()).transform(new RoundedCornersTransformation(10, 0)).into(ivProfileImage);


        TextView tvHandle = (TextView) view.findViewById(R.id.tvScreenName);
        tvHandle.setText(selectedTweet.getUser().getScreeName());

        TextView tvScreenName = (TextView) view.findViewById(R.id.tvScreenName);
        tvScreenName.setText(selectedTweet.getUser().getName());
    }

    private void showKeyboardAndFocusOnBody(View editText) {
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void setupTweetButtonPressed(Button tweetButton) {
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DismissComposeTweetListener listener = (DismissComposeTweetListener) getActivity();
                listener.onCompleteUserInput(etComposeBody.getText().toString());
                dismiss();
            }
        });
    }
}
