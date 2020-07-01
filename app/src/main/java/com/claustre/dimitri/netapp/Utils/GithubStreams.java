package com.claustre.dimitri.netapp.Utils;

import com.claustre.dimitri.netapp.Models.GithubUser;
import com.claustre.dimitri.netapp.Models.GithubUserInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
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

    // 1 - Create a stream that will get user infos on Github API
    public static Observable<GithubUserInfo> streamFetchUserInfos(String username) {
        GithubService githubService = GithubService.retrofit.create(GithubService.class);
        return githubService.getUserInfos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // 2 - Create a stream that will :
    //     A. Fetch all users followed by "username"
    //     B. Return the first user of the list
    //     C. Fetch details of the first user
    public static Observable<GithubUserInfo> streamFetchUserFollowingAndFetchFirstUserInfos(String username){
        return streamFetchUserFollowing(username) // A.
                .map(new Function<List<GithubUser>, GithubUser>() {
                    @Override
                    public GithubUser apply(List<GithubUser> githubUsers) throws Throwable {
                        return githubUsers.get(0); // B.
                    }
                })
                .flatMap(new Function<GithubUser, Observable<GithubUserInfo>>() {
                    @Override
                    public Observable<GithubUserInfo> apply(GithubUser githubUser) throws Throwable {
                        // C.
                        return streamFetchUserInfos(githubUser.getLogin());
                    }
                });
    }
}
