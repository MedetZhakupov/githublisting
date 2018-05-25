package com.medetzhakupov.githublisting.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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


    public void navigateToDetails(String username) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailActivity.USERNAME, username);
        activity.startActivity(intent);
    }
}
