package com.github.githubmvvmdemo.dataSources.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("node_id")
    @Expose
    private var nodeId: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("full_name")
    @Expose
    private var fullName: String? = null

    @SerializedName("private")
    @Expose
    private var _private: Boolean? = null

    @SerializedName("owner")
    @Expose
    private var owner: Owner? = null

    @SerializedName("html_url")
    @Expose
    private var htmlUrl: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getNodeId(): String? {
        return nodeId
    }

    fun setNodeId(nodeId: String?) {
        this.nodeId = nodeId
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getFullName(): String? {
        return fullName
    }

    fun setFullName(fullName: String?) {
        this.fullName = fullName
    }

    fun getPrivate(): Boolean? {
        return _private
    }

    fun setPrivate(_private: Boolean?) {
        this._private = _private
    }

    fun getOwner(): Owner? {
        return owner
    }

    fun setOwner(owner: Owner?) {
        this.owner = owner
    }

    fun getHtmlUrl(): String? {
        return htmlUrl
    }

    fun setHtmlUrl(htmlUrl: String?) {
        this.htmlUrl = htmlUrl
    }


}