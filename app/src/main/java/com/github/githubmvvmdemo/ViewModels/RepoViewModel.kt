package com.github.githubmvvmdemo.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.github.githubmvvmdemo.data.GithubSearch
import com.github.githubmvvmdemo.data.Item
import com.github.githubmvvmdemo.domail.ApiClient
import com.github.githubmvvmdemo.domail.ApiService
import com.github.githubmvvmdemo.utils.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoViewModel : ViewModel() {
    val ItemsLiveData: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }
    val ItemsLiveSearchData: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }

               fun getTrendingRepoList() : LiveData<List<Item>>   {
                   ApiClient.getClient().create(ApiService::class.java).GetReposSearchList(Utility.GetDateBeforeTwomonth()).enqueue(object  : Callback<GithubSearch> {
                           override fun onResponse(call: Call<GithubSearch>, response: Response<GithubSearch>) {
                               if (response.body()!=null){
                                    ItemsLiveData.value = response.body()!!.getItems()!!

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

               fun observLiveData() : LiveData<List<Item>> {
                   return ItemsLiveData
               }



}