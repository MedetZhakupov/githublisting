package com.medetzhakupov.githublisting.di;

import android.app.Activity;

import com.medetzhakupov.githublisting.ui.home.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainActivityBuilders {

    @Binds
    Activity provideActiity(MainActivity activity);

    @ContributesAndroidInjector
    MainActivity homeActivity();
}
