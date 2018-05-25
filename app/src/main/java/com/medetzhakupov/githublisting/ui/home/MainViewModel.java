package com.medetzhakupov.githublisting.ui.home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.medetzhakupov.githublisting.R;
import com.medetzhakupov.githublisting.data.UserRepository;
import com.medetzhakupov.githublisting.model.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by Medet Zhakupov.
 */
public class MainViewModel extends ViewModel {

    private final UserRepository repository;

    private final BehaviorRelay<List<User>> usersRelay = BehaviorRelay.create();
    private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();
    private final BehaviorRelay<Boolean> loadingRelay = BehaviorRelay.create();

    @Inject
    public MainViewModel(UserRepository repository) {
        this.repository = repository;
        loadGithubUsers();
    }

    @SuppressLint("CheckResult")
    private void loadGithubUsers() {
        repository.getGithubUsers()
                .doOnSubscribe(__ -> loadingRelay.accept(true))
                .doOnEvent((d, t) -> loadingRelay.accept(false))
                .subscribe(usersUpdated(), onError());
    }

    Observable<Boolean> loading() {
        return loadingRelay;
    }

    Observable<List<User>> users() {
        return usersRelay;
    }

    Observable<Integer> error() {
        return errorRelay;
    }

    Consumer<List<User>> usersUpdated() {
        errorRelay.accept(-1);
        return usersRelay;
    }

    Consumer<Throwable> onError() {
        return throwable -> {
            Timber.e(throwable, "Error loading Users");
            errorRelay.accept(R.string.api_error_users);
        };
    }


}
