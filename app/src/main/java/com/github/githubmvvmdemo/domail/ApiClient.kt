package com.github.githubmvvmdemo.domail

import android.content.Context
import android.util.Log
import com.github.githubmvvmdemo.BuildConfig
import com.github.githubmvvmdemo.utils.AppConstant
import com.google.gson.GsonBuilder
import com.pixplicity.easyprefs.library.Prefs
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        //var BASE_URL = "https://www.eshopshub.com/rest/V1/"  /*live*/


        fun getClient(): Retrofit {
            val loggerInterceptor = HttpLoggingInterceptor()
            if(com.github.githubmvvmdemo.BuildConfig.DEBUG){
                loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }else{
                loggerInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(loggerInterceptor)
                .addInterceptor { chain ->
                    var request = chain.request()
                    val builder = request.newBuilder()

                    request = builder.build()
                    var response: Response? = null
                    var responceString: String? = ""
                    try {
                        response = chain.proceed(request)
                        responceString = String(response.body!!.bytes())
                    } catch (e: ConnectException) {

                    }

                    response = response!!.newBuilder().body(
                        ResponseBody.create(
                            response.body!!.contentType(),
                            responceString!!
                        )
                    ).build()
                    response
                }.protocols(
                    arrayListOf(Protocol.HTTP_1_1)
                ).build()

            var url = com.github.githubmvvmdemo.BuildConfig.SERVICE_API_URL


            return Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .client(okHttpClient)
                .baseUrl(url)
                .build()
        }
    }

}