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

public class ComposeFragment extends DialogFragment {
    private EditText etComposeBody;

    public ComposeFragment() {
        // Required empty public constructor
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
        Button btnDismiss = (Button) view.findViewById(R.id.btnDismiss);
        etComposeBody = (EditText) view.findViewById(R.id.etComposeBody);
        showKeyboardAndFocusOnBody(etComposeBody);
        TextView tvCharacterCount = (TextView) view.findViewById(R.id.tvCharacterCount);
        Button btnTweet = (Button) view.findViewById(R.id.btnDismiss);


    }

    private void showKeyboardAndFocusOnBody(View editText) {
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void tweetButtonPressed(View view) {
        DismissComposeTweetListener listener = (DismissComposeTweetListener) getActivity();
        listener.onCompleteUserInput(etComposeBody.getText().toString());
    }
}
