package com.medetzhakupov.githublisting.data;

import com.medetzhakupov.githublisting.model.User;
import com.medetzhakupov.githublisting.model.UserDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.Single;

/**
 * Created by Medet Zhakupov.
 */
@Singleton
public class UserRepository {

    private final Provider<RepoRequester> repoRequesterProvider;
    private final Scheduler scheduler;
    private final List<User> cachedGithubUsers = new ArrayList<>();
    private final Map<String, UserDetail> cachedGithubUserDetails = new HashMap<>();

    @Inject
    UserRepository(
            Provider<RepoRequester> repoRequesterProvider,
            @Named("network_scheduler") Scheduler scheduler) {
        this.repoRequesterProvider = repoRequesterProvider;
        this.scheduler = scheduler;
    }

    public Single<List<User>> getGithubUsers() {
        return Maybe.concat(cachedGithubUsers(), apiGithubUsers())
                .firstOrError()
                .subscribeOn(scheduler);
    }

    public Single<UserDetail> getGithubUserDetail(String username) {
        return Maybe.concat(cachedGithubUserDetail(username), apiGithubUserDetail(username))
                .firstOrError()
                .subscribeOn(scheduler);
    }

    private Maybe<List<User>> cachedGithubUsers() {
        return Maybe.create(e -> {
            if (!cachedGithubUsers.isEmpty()) {
                e.onSuccess(cachedGithubUsers);
            }
            e.onComplete();
        });
    }

    private Maybe<List<User>> apiGithubUsers() {
        return repoRequesterProvider.get().getGithubUser()
                .doOnSuccess(repos -> {
                    cachedGithubUsers.clear();
                    cachedGithubUsers.addAll(repos);
                })
                .toMaybe();
    }

    private Maybe<UserDetail> cachedGithubUserDetail(String username) {
        return Maybe.create(e -> {
            if (cachedGithubUserDetails.containsKey(username)) {
                e.onSuccess(cachedGithubUserDetails.get(username));
            }
            e.onComplete();
        });
    }

    private Maybe<UserDetail> apiGithubUserDetail(String username) {
        return repoRequesterProvider.get().getGihubUserDetail(username)
                .doOnSuccess(userDetail -> cachedGithubUserDetails.put(username, userDetail))
                .toMaybe();
    }
}
