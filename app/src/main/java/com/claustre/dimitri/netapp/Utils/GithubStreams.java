package com.claustre.dimitri.netapp.Utils;

import com.claustre.dimitri.netapp.Models.GithubUser;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by dimeli on 29/06/2020.
 */
public class GithubStreams {

    public static Observable<List<GithubUser>> streamFetchUserFollowing(String username) {
        GithubService githubService = GithubService.retrofit.create(GithubService.class);
        return githubService.getFollowing(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
