package com.claustre.dimitri.netapp.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.claustre.dimitri.netapp.Models.GithubUser;
import com.claustre.dimitri.netapp.R;
import com.claustre.dimitri.netapp.Utils.GithubCalls;

import java.util.List;

import Utils.NetworkAsyncTask;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//deprecated import butterknife.BindView;
//deprecated import butterknife.ButterKnife;
//deprecated import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener, NetworkAsyncTask.Listeners, GithubCalls.Callbacks {

    // 1 - Implement Callbacks GithubCalls.Callbacks


    // FOR DESIGN
    //deprecated @BindView(R.id.fragment_main_textview)

    private OnButtonClickedListener mCallback;
    private Button mButton;
    private TextView mTextView;


    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //deprecated ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextView = view.findViewById(R.id.fragment_main_textview);
        mButton = view.findViewById(R.id.fragment_main_button);
        mButton.setOnClickListener(this);

        mCallback = (OnButtonClickedListener) getActivity();
    }


    // -----------------
    // ACTIONS
    // -----------------

    //deprecated @OnClick(R.id.fragment_main_button)

    public void submit(View view) {
        // executeHttpRequest();
        executeHttpRequestWithRetrofit();
    }


    public interface OnButtonClickedListener {
        void onButtonClicked(View view);
    }

    @Override
    public void onClick(View view) {
        mCallback.onButtonClicked(view);
        Log.e("TAG", "Click !");

        submit(view);
    }


    // -----------------
    // HTTP REQUEST
    // -----------------

    private void executeHttpRequest() {
        new NetworkAsyncTask(this).execute("https://api.github.com/users/JakeWharton/following");
    }

    @Override
    public void onPreExecute() {
        this.updateUIWhenStartingHTTPRequest();
    }

    @Override
    public void doInBackground() {
    }

    @Override
    public void onPostExecute(String json) {
        this.updateUIWhenStopingHTTPRequest(json);
    }


    // -----------------
    // HTTP REQUEST (Retrofit Way)
    // -----------------

    // 2 - Override callback methods

    @Override
    public void onResponse(@Nullable List<GithubUser> users) {
        // 2.1 - When getting response, we update UI
        if (users != null) this.updateUIWithListOfUsers(users);
    }

    @Override
    public void onFailure() {
        // 2.2 - When getting error, we update UI
        this.updateUIWhenStopingHTTPRequest("An error happened !");
    }

    // 4 - Execute HTTP request and update UI
    private void executeHttpRequestWithRetrofit() {
        this.updateUIWhenStartingHTTPRequest();
        GithubCalls.fetchUserFollowing(this, "JakeWharton");
    }


    // -----------------
    // UPDATE UI
    // -----------------

    private void updateUIWhenStartingHTTPRequest() {
        this.mTextView.setText("Downloading...");
    }

    private void updateUIWhenStopingHTTPRequest(String response) {
        this.mTextView.setText(response);
    }

    // 3 - Update UI showing only name of users
    private void updateUIWithListOfUsers(List<GithubUser> users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (GithubUser user : users) {
            stringBuilder.append("-" + user.getLogin() + "\n");
        }
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }

}
