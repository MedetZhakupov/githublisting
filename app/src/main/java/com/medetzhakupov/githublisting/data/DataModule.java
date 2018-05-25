package com.medetzhakupov.githublisting.data;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Medet Zhakupov.
 */
@Module
public class DataModule {

    @Provides
    public GithubService provideGithubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }
}
