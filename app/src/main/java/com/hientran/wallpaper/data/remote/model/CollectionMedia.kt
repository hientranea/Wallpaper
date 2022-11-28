package com.hientran.wallpaper.data.remote.model

import com.google.gson.annotations.SerializedName

data class CollectionMedia(
    @SerializedName("id") val id: String = "",
    @SerializedName("page") val page: Int = 1,
    @SerializedName("per_page") val perPage: Int = 30,
    @SerializedName("total_results") val total: Int = 0,
    @SerializedName("media") val photos: List<WallpaperPhoto> = emptyList(),
)
