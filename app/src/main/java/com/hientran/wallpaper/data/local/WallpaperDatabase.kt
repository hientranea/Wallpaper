package com.hientran.wallpaper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hientran.wallpaper.data.local.dao.RemoteKeyDao
import com.hientran.wallpaper.data.local.dao.SearchDao
import com.hientran.wallpaper.data.local.dao.WallpaperDao
import com.hientran.wallpaper.data.local.entities.RemoteKeyEntity
import com.hientran.wallpaper.data.local.entities.SearchEntity
import com.hientran.wallpaper.data.local.entities.WallpaperEntity

@Database(
    entities = [
        WallpaperEntity::class,
        RemoteKeyEntity::class,
        SearchEntity::class
    ], version = 1, exportSchema = false
)

abstract class WallpaperDatabase: RoomDatabase() {
    abstract fun wallpaperDao(): WallpaperDao

    abstract fun searchDao(): SearchDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}
