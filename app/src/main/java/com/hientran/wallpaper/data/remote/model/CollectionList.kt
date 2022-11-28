package com.hientran.wallpaper.data.remote.model

import com.google.gson.annotations.SerializedName

data class CollectionList(
    @SerializedName("page") val page: Int = 1,
    @SerializedName("per_page") val perPage: Int = 30,
    @SerializedName("collections") val collections: List<PhotoCollection> = emptyList(),
    @SerializedName("total_results") val total: Int = 0,
)
