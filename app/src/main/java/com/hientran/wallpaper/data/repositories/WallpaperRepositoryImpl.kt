package com.hientran.wallpaper.data.repositories

import com.hientran.wallpaper.data.WallpaperDataSource
import com.hientran.wallpaper.data.WallpaperLocalDataSource
import com.hientran.wallpaper.data.di.LocalDataSource
import com.hientran.wallpaper.data.local.entities.SearchEntity
import com.hientran.wallpaper.domain.repositories.WallpaperRepository

class WallpaperRepositoryImpl(
    @LocalDataSource private val wallpaperDataSource: WallpaperDataSource
): WallpaperRepository {
    override suspend fun saveSearchQuery(query: String): SearchEntity {
        return (wallpaperDataSource as WallpaperLocalDataSource).saveSearchQuery(query)
    }
}
