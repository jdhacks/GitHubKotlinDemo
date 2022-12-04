package com.github.githubmvvmdemo.domail

import com.github.githubmvvmdemo.data.*
import com.github.githubmvvmdemo.utils.ApiConstant
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {



    @GET(ApiConstant.REPO)
    fun GetReposSearchList(
        @Query("q") q: String? = "created:'>2022-09-18'",
        @Query("sort") sort1: String? = "updated",
    @Query("order") order1: String? = "desc",
    @Query("sort") sort2: String? = "stars",
    @Query("order") order2: String? = "desc",
    @Query("since") since: String? = "daily"
    ): Call<GithubSearch>

}