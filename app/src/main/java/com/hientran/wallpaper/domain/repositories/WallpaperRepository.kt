package com.hientran.wallpaper.domain.repositories

import com.hientran.wallpaper.data.local.entities.SearchEntity

interface WallpaperRepository {
    suspend fun saveSearchQuery(query: String): SearchEntity
}
