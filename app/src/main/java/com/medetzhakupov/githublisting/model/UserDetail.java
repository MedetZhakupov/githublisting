package com.medetzhakupov.githublisting.model;

import com.squareup.moshi.Json;

/**
 * Created by Medet Zhakupov.
 */
public final class UserDetail {

    public final int id;

    public final String login;

    @Json(name = "avatar_url")
    public final String avatarUrl;

    @Json(name = "site_admin")
    public final boolean siteAdmin;

    public final String name;

    public final String bio;

    public final String location;

    public final String blog;

    public UserDetail(int id, String login, String avatarUrl, boolean siteAdmin, String name, String bio, String location, String blog) {
        this.id = id;
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.siteAdmin = siteAdmin;
        this.name = name;
        this.bio = bio;
        this.location = location;
        this.blog = blog;
    }
}
