package com.medetzhakupov.githublisting.network;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Medet Zhakupov.
 */
@Module
public abstract class NetworkModule {

    @Provides
    @Singleton
    static Call.Factory provideOkHttp() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Named("base_url")
    static String provideBaseUrl() {
        return "https://api.github.com/";
    }
}
