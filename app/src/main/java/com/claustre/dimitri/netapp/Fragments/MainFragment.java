package com.claustre.dimitri.netapp.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.claustre.dimitri.netapp.R;

import Utils.NetworkAsyncTask;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener, NetworkAsyncTask.Listeners {

    // FOR DESIGN
    //@BindView(R.id.fragment_main_textview)

    private OnButtonClickedListener mCallback;
    private Button mButton;
    private TextView mTextView;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //ButterKnife.bind(this, view);
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

    //@OnClick(R.id.fragment_main_button)

    public void submit(View view) {
        executeHttpRequest();
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
        this.updateUIWhenStoppingHTTPResquest(json);
    }


    // -----------------
    // UPDATE UI
    // -----------------

    private void updateUIWhenStartingHTTPRequest() {
        this.mTextView.setText("Downloading...");
    }

    private void updateUIWhenStoppingHTTPResquest(String response) {
        this.mTextView.setText(response);
    }
}
