package com.medetzhakupov.githublisting.ui.detail;

import android.arch.lifecycle.ViewModel;

import com.medetzhakupov.githublisting.data.UserRepository;

import javax.inject.Inject;

/**
 * Created by Medet Zhakupov.
 */
public class DetailViewModel extends ViewModel {

    private final UserRepository repository;

    @Inject
    DetailViewModel(UserRepository repository) {
        this.repository = repository;
    }
}
