package com.hientran.wallpaper.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "search_results", indices = [Index(value = ["query"], unique = true)])
data class SearchEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val query: String = "",
)
