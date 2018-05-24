package com.medetzhakupov.githublisting.data;

import com.medetzhakupov.githublisting.model.User;
import com.medetzhakupov.githublisting.model.UserDetail;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Medet Zhakupov.
 */
public interface GithubService {

    @GET("users")
    Single<User> getUsers(@Query("since") String since);

    @GET("users/{username}")
    Single<UserDetail> getUserDetails(@Path("username") String username);

}
