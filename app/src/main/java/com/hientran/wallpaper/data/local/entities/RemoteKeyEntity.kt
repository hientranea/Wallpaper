package com.hientran.wallpaper.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val label: String="",
    val nextPage: Int?,
    val nLastItems: Int = 0
)
