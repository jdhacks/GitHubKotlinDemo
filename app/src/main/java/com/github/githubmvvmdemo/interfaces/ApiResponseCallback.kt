package com.github.githubmvvmdemo.interfaces

interface ApiResponseCallback {
    fun onSuccess()
    fun onFailed(errorMessage: String)
}