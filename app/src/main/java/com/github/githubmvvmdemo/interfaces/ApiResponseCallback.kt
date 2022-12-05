package com.github.githubmvvmdemo.interfaces

import com.github.githubmvvmdemo.dataSources.remote.Owner

interface ApiResponseCallback {
    fun onSuccess()
    fun onFailed(errorMessage : String)
}