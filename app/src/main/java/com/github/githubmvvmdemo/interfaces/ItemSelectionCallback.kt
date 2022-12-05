package com.github.githubmvvmdemo.interfaces

import com.github.githubmvvmdemo.dataSources.remote.Owner

interface ItemSelectionCallback {
    fun onClick(item: Owner?, position: Int)
}