package com.hientran.wallpaper.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallpapers")
data class WallpaperEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var photoId: Long = -1,
    val width: Int = 0,
    val height: Int = 0,
    val portraitUrl: String = "",
    val mediumUrl: String = "",
    val collectionId: String = "",
    var searchId: Long? = null
)
