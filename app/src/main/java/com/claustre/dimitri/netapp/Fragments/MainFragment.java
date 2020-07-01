package com.claustre.dimitri.netapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.claustre.dimitri.netapp.Models.GithubUser;
import com.claustre.dimitri.netapp.Models.GithubUserInfo;
import com.claustre.dimitri.netapp.R;
import com.claustre.dimitri.netapp.Utils.GithubStreams;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    // FOR DESIGN
    private OnButtonClickedListener mCallback;
    private Button mButton;
    private TextView mTextView;

    // FOR DATA
    private Disposable disposable;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeWhenDestroy();
    }


    // -----------------
    // ACTIONS
    // -----------------

    public void submit(View view) {
        // executeHttpRequest();
        //executeHttpRequestWithRetrofit();
        //streamShowString();

        // Call the Stream
        //executeHttpRequestWithRetrofit();
        executeSecondHttpRequestWithRetrofit();
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
    // HTTP (RxJAVA)
    // -----------------

    // Execute our Stream
    private void executeHttpRequestWithRetrofit() {
        // Update UI
        this.updateUIWhenStartingHTTPRequest();
        // Excecute the stream suscribing to Observable defined inside GithubStream
        this.disposable = GithubStreams.streamFetchUserFollowing("JakeWharton").subscribeWith(new DisposableObserver<List<GithubUser>>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<GithubUser> githubUsers) {
                Log.e("TAG", "On Next");
                // Update UI with list of users
                updateUIWithListOfUsers(githubUsers);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.e("TAG", "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On Complete !!");
            }
        });
    }


    private void executeSecondHttpRequestWithRetrofit() {
        this.updateUIWhenStartingHTTPRequest();
        this.disposable = GithubStreams.streamFetchUserFollowingAndFetchFirstUserInfos("JakeWharton").subscribeWith(new DisposableObserver<GithubUserInfo>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull GithubUserInfo githubUserInfo) {
                Log.e("TAG", "On Next");
                updateUIWithUserInfo(githubUserInfo);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.e("TAG", "On Error" + Log.getStackTraceString(e));

            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On Complete !!");
            }
        });
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
    }


    // -----------------
    // UPDATE UI
    // -----------------

    private void updateUIWithUserInfo(GithubUserInfo userInfo) {
        updateUIWhenStopingHTTPRequest("The first Following of Jake Wharton is " + userInfo.getName() + " with " + userInfo.getFollowers() + " followers.");
    }

    private void updateUIWhenStartingHTTPRequest() {
        this.mTextView.setText("Downloading...");
    }

    private void updateUIWhenStopingHTTPRequest(String response) {
        this.mTextView.setText(response);
    }

    // Update UI showing only name of users
    private void updateUIWithListOfUsers(List<GithubUser> users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (GithubUser user : users) {
            stringBuilder.append("-" + user.getLogin() + "\n");
        }
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }
}