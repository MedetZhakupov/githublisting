package com.medetzhakupov.githublisting.di;

import com.medetzhakupov.githublisting.ui.detail.DetailActivity;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by Medet Zhakupov.
 */
@ActivityScope
@Subcomponent
public interface DetailActivityComponent extends AndroidInjector<DetailActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DetailActivity> {

        @BindsInstance
        public abstract void bindUsername(@Named("username") String username);

        @Override
        public void seedInstance(DetailActivity instance) {
            bindUsername(instance.getIntent().getStringExtra(DetailActivity.USERNAME));
        }
    }
}
