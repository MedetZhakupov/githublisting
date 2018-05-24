package com.medetzhakupov.githublisting.di;

import com.medetzhakupov.githublisting.ui.detail.DetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Medet Zhakupov.
 */
@Module
public interface DetailsActivityBuilder {

    @ContributesAndroidInjector
    DetailActivity detailActivity();
}
