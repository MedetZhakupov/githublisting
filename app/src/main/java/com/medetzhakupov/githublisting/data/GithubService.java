package com.medetzhakupov.githublisting.data;

import com.medetzhakupov.githublisting.model.User;
import com.medetzhakupov.githublisting.model.UserDetail;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Medet Zhakupov.
 */
public interface GithubService {

    @GET("users")
    Single<List<User>> getUsers(@Query("since") int since);

    @GET("users/{username}")
    Single<UserDetail> getUserDetails(@Path("username") String username);

}
