package com.medetzhakupov.githublisting.model;

import com.squareup.moshi.Json;

/**
 * Created by Medet Zhakupov.
 */
public final class User {

    public final int id;

    public final String login;

    @Json(name = "avatar_url")
    public final String avatarUrl;

    @Json(name = "site_admin")
    public final boolean siteAdim;

    public User(int id, String login, String avatarUrl, boolean siteAdim) {
        this.id = id;
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.siteAdim = siteAdim;
    }
}
