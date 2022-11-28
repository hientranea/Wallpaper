package com.hientran.wallpaper.data.remote.model

import com.google.gson.annotations.SerializedName

data class WallpaperList(
    @SerializedName("page") val page: Int = 1,
    @SerializedName("per_page") val perPage: Int = 30,
    @SerializedName("photos") val photos: List<WallpaperPhoto> = emptyList(),
)
