package com.github.githubmvvmdemo.interfaces

import com.github.githubmvvmdemo.dataSources.remote.Owner

interface AlertDialogCallback {
    fun onOkClick()
    fun onRetryClick()
}