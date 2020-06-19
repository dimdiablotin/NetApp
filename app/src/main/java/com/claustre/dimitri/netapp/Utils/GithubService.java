package com.claustre.dimitri.netapp.Utils;

import com.claustre.dimitri.netapp.Models.GithubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dimeli on 19/06/2020.
 */
public interface GithubService {
    @GET("users/{username}/following")
    Call<List<GithubUser>> getFollowing(@Path("username") String username);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
