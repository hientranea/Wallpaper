package com.hientran.wallpaper.data.model

import com.google.gson.annotations.SerializedName

data class PhotoCollection(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("photos_count") val photosCount: Int = 0,
)
