package com.github.githubmvvmdemo.dataSources.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GithubSearch {
    @SerializedName("total_count")
    @Expose
    private var totalCount: Int? = null

    @SerializedName("incomplete_results")
    @Expose
    private var incompleteResults: Boolean? = null

    @SerializedName("items")
    @Expose
    private var items: ArrayList<Item>? = null

    fun getTotalCount(): Int? {
        return totalCount
    }

    fun setTotalCount(totalCount: Int?) {
        this.totalCount = totalCount
    }

    fun getIncompleteResults(): Boolean? {
        return incompleteResults
    }

    fun setIncompleteResults(incompleteResults: Boolean?) {
        this.incompleteResults = incompleteResults
    }

    fun getItems(): ArrayList<Item>? {
        return items
    }

    fun setItems(items: ArrayList<Item>?) {
        this.items = items
    }

}