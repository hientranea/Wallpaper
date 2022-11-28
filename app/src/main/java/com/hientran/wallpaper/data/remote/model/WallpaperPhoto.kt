package com.hientran.wallpaper.data.remote.model

import com.google.gson.annotations.SerializedName
import com.hientran.wallpaper.data.local.entities.WallpaperEntity

data class WallpaperPhoto(
    @SerializedName("id") val id: Long = -1L,
    @SerializedName("width") val width: Int = 0,
    @SerializedName("height") val height: Int = 0,
    @SerializedName("src") val urls: WallpaperUrl? = null
)

data class WallpaperUrl(
    @SerializedName("original") val original: String = "",
    @SerializedName("large2x") val large2x: String = "",
    @SerializedName("large") val large: String = "",
    @SerializedName("medium") val medium: String = "",
    @SerializedName("small") val small: String = "",
    @SerializedName("portrait") val portrait: String = "",
    @SerializedName("landscape") val landscape: String = "",
    @SerializedName("tiny") val tiny: String = ""
)

fun WallpaperPhoto.toWallpaperEntity(collectionId: String? = null, searchId: Long? = null): WallpaperEntity {
    return WallpaperEntity(
        photoId = id,
        width = width,
        height = height,
        mediumUrl = urls?.medium ?: "",
        portraitUrl = urls?.portrait ?: "",
        collectionId = collectionId ?: "",
        searchId = searchId
    )
}
