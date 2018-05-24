package com.medetzhakupov.githublisting.di;

import android.app.Application;

import com.medetzhakupov.githublisting.data.GithubService;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import javax.inject.Singleton;

import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Medet Zhakupov.
 */
public class DataModule {

    Application application;

    public DataModule(Application application) {
        this.application = application;
    }

    @Provides
    public GithubService provideGithubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                //converts Retrofit response into Observable
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public OkHttpClient provideHttpClient() {
        return new OkHttpClient().newBuilder().build();
    }


}
