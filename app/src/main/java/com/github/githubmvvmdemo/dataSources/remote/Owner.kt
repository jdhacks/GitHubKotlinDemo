package com.github.githubmvvmdemo.dataSources.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Owner {
    @SerializedName("login")
    @Expose
    private var login: String? = null

    @SerializedName("avatar_url")
    @Expose
    private var avatarUrl: String? = null

    fun getSelected(): Boolean {
        return isSelected
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }

    private var isSelected = false

    fun getLogin(): String? {
        return login
    }

    fun getAvatarUrl(): String? {
        return avatarUrl
    }

}