package com.medetzhakupov.githublisting.model;

import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

/**
 * Created by Medet Zhakupov.
 */
@MoshiAdapterFactory
public abstract class AdapterFactory implements JsonAdapter.Factory {

    public static JsonAdapter.Factory create() {
        return new AutoValueMoshi_AdapterFactory();
    }
}
