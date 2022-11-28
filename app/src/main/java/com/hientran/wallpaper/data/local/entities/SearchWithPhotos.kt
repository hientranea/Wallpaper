package com.hientran.wallpaper.data.local.entities

import androidx.room.Embedded

data class  SearchWithPhotos(
    @Embedded val search : SearchEntity
)
