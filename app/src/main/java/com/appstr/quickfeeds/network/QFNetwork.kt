package com.appstr.quickfeeds.network

import android.app.Application
import android.util.Log
import com.appstr.quickfeeds.model.ImageData
import com.appstr.quickfeeds.pattern.SingletonHolder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QFNetwork(appliContext: Application) {

    companion object : SingletonHolder<QFNetwork, Application>(::QFNetwork){
        // ie: https://picsum.photos
        private val imagesUrl = "https://picsum.photos/"

        private var retrofitClient: Retrofit = Retrofit.Builder()
            .baseUrl(imagesUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun requestImageFeed(callback: Callback<List<ImageData>>){
            GlobalScope.launch {
                retrofitClient.create(QFRetrofitService::class.java)
                    .getImageFeed()
                    .enqueue(callback)
            }
        }

        fun logFailure(call: Call<List<ImageData>>, t: Throwable){
            Log.d("uwotm8", "QFNetwork    -    QFNetwork.requestFeed()    -    98    -    FAILURE")
            Log.d("uwotm8", "QFNetwork    -    99    -    ${t.message}")
            Log.d("uwotm8", "QFNetwork    -    99    -    ${t.localizedMessage}")
            Log.d("uwotm8", "QFNetwork    -    99    -    ${t.cause}")
            Log.d("uwotm8", "QFNetwork    -    99    -    ${t.suppressed}")
            Log.d("uwotm8", "QFNetwork    -    99    -    ${t.stackTrace}")
        }
    }

}