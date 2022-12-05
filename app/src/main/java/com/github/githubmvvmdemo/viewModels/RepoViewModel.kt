package com.github.githubmvvmdemo.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.githubmvvmdemo.dataSources.remote.GithubSearch
import com.github.githubmvvmdemo.dataSources.remote.Item
import com.github.githubmvvmdemo.domain.ApiClient
import com.github.githubmvvmdemo.domain.ApiService
import com.github.githubmvvmdemo.interfaces.ApiResponseCallback
import com.github.githubmvvmdemo.utils.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoViewModel : ViewModel() {
    var isNetworkAvailable: Boolean = false
    var erroMessage: String = ""
    val itemsLiveData: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }
    val itemsLiveSearchData: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }

               fun getTrendingRepoList( apiResponseCallback : ApiResponseCallback ) : LiveData<List<Item>>   {
                   ApiClient.getClient().create(ApiService::class.java).GetReposSearchList(Utility.GetDateBeforeTwomonth()).enqueue(object  : Callback<GithubSearch> {
                           override fun onResponse(call: Call<GithubSearch>, response: Response<GithubSearch>) {
                               if (response.body()!=null){
                                    itemsLiveData.value = response.body()?.getItems().orEmpty()
                                   apiResponseCallback.onSuccess()
                               }
                               else{
                                   return
                               }
                           }
                           override fun onFailure(call: Call<GithubSearch>, t: Throwable) {
                               Log.d("TAG",t.message.toString())
                               erroMessage = t.message.orEmpty()
                               apiResponseCallback.onFailed(erroMessage)
                           }
                       })
                   return itemsLiveData
               }

               fun observLiveData() : LiveData<List<Item>> {
                   return itemsLiveData
               }



}