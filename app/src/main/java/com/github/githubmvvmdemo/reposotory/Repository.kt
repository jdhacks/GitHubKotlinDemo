package com.github.githubmvvmdemo.reposotory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.githubmvvmdemo.dataSources.remote.GithubSearch
import com.github.githubmvvmdemo.dataSources.remote.Item
import com.github.githubmvvmdemo.domain.ApiClient
import com.github.githubmvvmdemo.domain.ApiService
import com.github.githubmvvmdemo.utils.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository() {
    val ItemsLiveData: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }
    fun getTrendingRepoList() : LiveData<List<Item>> {
        ApiClient.getClient().create(ApiService::class.java).GetReposSearchList(Utility.GetDateBeforeTwomonth()).enqueue(object  :
            Callback<GithubSearch> {
            override fun onResponse(call: Call<GithubSearch>, response: Response<GithubSearch>) {
                if (response.body()!=null){
                    ItemsLiveData.value = response.body()?.getItems().orEmpty()

                }
                else{
                    return
                }
            }
            override fun onFailure(call: Call<GithubSearch>, t: Throwable) {
                Log.d("TAG",t.message.toString())
            }
        })
        return ItemsLiveData
    }
}