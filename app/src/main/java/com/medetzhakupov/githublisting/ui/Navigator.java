package com.medetzhakupov.githublisting.ui;

import android.app.Activity;
import android.content.Intent;

import com.medetzhakupov.githublisting.ui.detail.DetailActivity;

import javax.inject.Inject;

/**
 * Created by Medet Zhakupov.
 */
public class Navigator {

    private final Activity activity;

    @Inject
    public Navigator(Activity activity) {
        this.activity = activity;
    }


    public void navigateToDetails() {
        Intent intent = new Intent(activity, DetailActivity.class);
        activity.startActivity(intent);
    }
}
