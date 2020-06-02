package com.appstr.quickfeeds.network

import com.appstr.quickfeeds.model.ImageData
import retrofit2.Call
import retrofit2.http.GET

interface QFRetrofitService {
    // https://picsum.photos + /v2/list?page=2&limit=100
    @GET("/v2/list?page=2&limit=100")
    fun getImageFeed(): Call<List<ImageData>>

}