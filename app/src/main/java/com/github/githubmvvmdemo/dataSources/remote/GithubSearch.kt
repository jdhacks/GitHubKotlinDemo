package com.github.githubmvvmdemo.dataSources.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GithubSearch {

    @SerializedName("items")
    @Expose
    private var items: ArrayList<Item>? = null

    fun getItems(): ArrayList<Item>? {
        return items
    }

}