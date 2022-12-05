package com.github.githubmvvmdemo.dataSources.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "owners")
data class OwnerEntity(
    private var login: String? = null,

    private var id: Int? = null,


    private var nodeId: String? = null,


    private var avatarUrl: String? = null,


    private var gravatarId: String? = null,

    private var url: String? = null,


    private var htmlUrl: String? = null,


    private var followersUrl: String? = null,


    private var followingUrl: String? = null,


    private var gistsUrl: String? = null,


    private var starredUrl: String? = null,


    private var subscriptionsUrl: String? = null,


    private var organizationsUrl: String? = null,


    private var reposUrl: String? = null,


    private var eventsUrl: String? = null,


    private var receivedEventsUrl: String? = null,

    private var type: String? = null,
    private var siteAdmin: Boolean? = null

)
