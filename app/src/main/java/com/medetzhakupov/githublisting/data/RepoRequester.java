package com.medetzhakupov.githublisting.data;

import com.medetzhakupov.githublisting.model.User;
import com.medetzhakupov.githublisting.model.UserDetail;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by Medet Zhakupov.
 */
public class RepoRequester {

    private final GithubService service;

    @Inject
    RepoRequester(GithubService service) {
        this.service = service;
    }

    Single<List<User>> getGithubUser() {
        return service.getUsers(0);
    }

    Single<UserDetail> getGihubUserDetail(String username) {
        return service.getUserDetails(username);
    }
}
