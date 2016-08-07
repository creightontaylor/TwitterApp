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
import com.codepath.apps.mysimpletweets.models.User;

import org.parceler.Parcels;

public class ComposeFragment extends DialogFragment {
    private EditText etComposeBody;
    private User currentUser;

    public ComposeFragment() {
        // Required empty public constructor
    }

    public static ComposeFragment newInstance(User user) {
        ComposeFragment frag = new ComposeFragment();
        Bundle args = new Bundle();
        args.putParcelable("currentUser", Parcels.wrap(user));
        frag.setArguments(args);
        frag.currentUser = user;
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

        ImageView ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
        TextView tvHandle = (TextView) view.findViewById(R.id.tvScreenName);
        TextView tvScreenName = (TextView) view.findViewById(R.id.tvScreenName);
        TextView tvCharacterCount = (TextView) view.findViewById(R.id.tvCharacterCount);

        etComposeBody = (EditText) view.findViewById(R.id.etComposeBody);
        showKeyboardAndFocusOnBody(etComposeBody);

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
