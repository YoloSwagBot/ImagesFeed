package com.appstr.quickfeeds.model

import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("id")
    var id: Int,

    @SerializedName("author")
    var author: String,

    @SerializedName("width")
    var width: Int,

    @SerializedName("height")
    var height: Int,

    @SerializedName("download_url")
    var imgUrl: String

)