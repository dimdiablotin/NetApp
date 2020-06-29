package com.claustre.dimitri.netapp.Utils;

import com.claustre.dimitri.netapp.Models.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dimeli on 19/06/2020.
 */
public interface GithubService {
    @GET("users/{username}/following")
    Observable<List<GithubUser>> getFollowing(@Path("username") String username);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();
}
