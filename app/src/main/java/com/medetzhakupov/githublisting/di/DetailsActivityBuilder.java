package com.medetzhakupov.githublisting.di;

import android.app.Activity;

import com.medetzhakupov.githublisting.ui.detail.DetailActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by Medet Zhakupov.
 */
@Module(
        subcomponents = DetailActivityComponent.class
)
public interface DetailsActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(DetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindTrendingReposInjector(DetailActivityComponent.Builder builder);
}
