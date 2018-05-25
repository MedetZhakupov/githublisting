package com.medetzhakupov.githublisting.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseActivity extends DaggerAppCompatActivity {

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        disposables.addAll(subscriptions());
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposables.clear();
    }

    protected Disposable[] subscriptions() {
        return new Disposable[0];
    }
    
}
