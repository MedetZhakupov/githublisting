package com.medetzhakupov.githublisting.ui.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.medetzhakupov.githublisting.R;
import com.medetzhakupov.githublisting.data.UserRepository;
import com.medetzhakupov.githublisting.model.User;
import com.medetzhakupov.githublisting.model.UserDetail;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by Medet Zhakupov.
 */
public class DetailViewModel extends ViewModel {

    private final UserRepository repository;

    private final BehaviorRelay<UserDetail> userDetailRelay = BehaviorRelay.create();
    private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();
    private final BehaviorRelay<Boolean> loadingRelay = BehaviorRelay.create();

    private String username;

    @Inject
    DetailViewModel(UserRepository repository) {
        this.repository = repository;
    }

    public void setUsername(String username) {
        this.username = username;
        loadGithubUserDetails(this.username);
    }

    @SuppressLint("CheckResult")
    private void loadGithubUserDetails(String username) {
        repository.getGithubUserDetail(username)
                .doOnSubscribe(__ -> loadingRelay.accept(true))
                .doOnEvent((d, t) -> loadingRelay.accept(false))
                .subscribe(usersUpdated(), onError());
    }

    Observable<Boolean> loading() {
        return loadingRelay;
    }

    Observable<UserDetail> userDetail() {
        return userDetailRelay;
    }

    Observable<Integer> error() {
        return errorRelay;
    }

    Consumer<UserDetail> usersUpdated() {
        errorRelay.accept(-1);
        return userDetailRelay;
    }

    Consumer<Throwable> onError() {
        return throwable -> {
            Timber.e(throwable, "Error loading user detail");
            errorRelay.accept(R.string.api_error_user_detail);
        };
    }
}
