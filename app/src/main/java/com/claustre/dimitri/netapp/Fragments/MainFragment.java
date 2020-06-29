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
import com.claustre.dimitri.netapp.Utils.GithubStreams;
//import com.claustre.dimitri.netapp.Utils.GithubCalls;

import java.util.List;

import Utils.NetworkAsyncTask;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;


//deprecated import butterknife.BindView;
//deprecated import butterknife.ButterKnife;
//deprecated import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    // Implement Callbacks GithubCalls.Callbacks


    // FOR DESIGN
    //deprecated @BindView(R.id.fragment_main_textview)

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeWhenDestroy();
    }

    // -----------------
    // ACTIONS
    // -----------------

    //deprecated @OnClick(R.id.fragment_main_button)

    public void submit(View view) {
        // executeHttpRequest();
        //executeHttpRequestWithRetrofit();
        //streamShowString();
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

    //// -----------------
    //// Reactive X
    //// -----------------
//
    //// 1 Create Observable
    //private Observable<String> getObservable() {
    //    return Observable.just("Cool !");
    //}
//
    //// 2 - Create Subscriber
    //private DisposableObserver<String> getSubscriber() {
    //    return new DisposableObserver<String>() {
    //        @Override
    //        public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {
    //            mTextView.setText("Observable emits : " + s);
//
    //        }
//
    //        @Override
    //        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
    //            Log.e("TAG", "On Error !!" + Log.getStackTraceString(e));
    //        }
//
    //        @Override
    //        public void onComplete() {
    //            Log.e("TAG", "On Complete !!");
    //        }
    //    };
    //}
//
    //// 3 - Create Stream and execute it
    //private void streamShowString() {
    //    this.disposable = this.getObservable()
    //            .map(getFunctionUpperCase())
    //            .flatMap(getSecondObservable()) // Y - Adding Observable
    //            .subscribeWith(getSubscriber());
    //}
//
    //// 5 Dispose subscription
    //private void disposeWhenDestroy() {
    //    if (this.disposable != null && !disposable.isDisposed()) {
    //        this.disposable.dispose();
    //    }
    //}
//
    //// A - Create function to Uppercase a String
    //private Function<String, String> getFunctionUpperCase() {
    //    return new Function<String, String>() {
    //        @Override
    //        public String apply(String s) throws Throwable {
    //            return s.toUpperCase();
    //        }
    //    };
    //}
//
    //// X - Create a function that will calling a new observable
    //private Function<String, Observable<String>> getSecondObservable(){
    //    return new Function<String, Observable<String>>() {
    //        @Override
    //        public Observable<String> apply(String previousString) throws Throwable {
    //            return Observable.just(previousString + " I love OpenClassRooms !");
    //        }
    //    };
    //}
//


    //// -----------------
    //// HTTP REQUEST
    //// -----------------
//
    //private void executeHttpRequest() {
    //    new NetworkAsyncTask(this).execute("https://api.github.com/users/JakeWharton/following");
    //}
//
    //@Override
    //public void onPreExecute() {
    //    this.updateUIWhenStartingHTTPRequest();
    //}
//
    //@Override
    //public void doInBackground() {
    //}
//
    //@Override
    //public void onPostExecute(String json) {
    //    this.updateUIWhenStopingHTTPRequest(json);
    //}


    //// -----------------
    //// HTTP REQUEST (Retrofit Way)
    //// -----------------
//
    //// Override callback methods
//
    //@Override
    //public void onResponse(@Nullable List<GithubUser> users) {
    //    // When getting response, we update UI
    //    if (users != null) this.updateUIWithListOfUsers(users);
    //}
//
    //@Override
    //public void onFailure() {
    //    // When getting error, we update UI
    //    this.updateUIWhenStopingHTTPRequest("An error happened !");
    //}
//
    //// Execute HTTP request and update UI
    //private void executeHttpRequestWithRetrofit() {
    //    this.updateUIWhenStartingHTTPRequest();
    //    GithubCalls.fetchUserFollowing(this, "JakeWharton");
    //}


    // -----------------
    // HTTP (RxJAVA)
    // -----------------

    // 1 - Execute our Stream
    private void executeHttpRequestWithRetrofit() {
        // 1.1 - Update UI
        this.updateUIWhenStartingHTTPRequest();
        // 1.2 - Excecute the stream suscribing to Observable defined inside GithubStream
        this.disposable = GithubStreams.streamFetchUserFollowing("JakeWharton").subscribeWith(new DisposableObserver<List<GithubUser>>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<GithubUser> githubUsers) {
                Log.e("TAG", "On Next");
                // 1.3 - Update UI with list of users
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

    private void disposeWhenDestroy() {
        if(this.disposable != null && !this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
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

    // Update UI showing only name of users
    private void updateUIWithListOfUsers(List<GithubUser> users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (GithubUser user : users) {
            stringBuilder.append("-" + user.getLogin() + "\n");
        }
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }
}
