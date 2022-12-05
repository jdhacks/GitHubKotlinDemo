package com.github.githubmvvmdemo.dataSources.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {

    @SerializedName("owner")
    @Expose
    private var owner: Owner? = null

    fun getOwner(): Owner? {
        return owner
    }


}