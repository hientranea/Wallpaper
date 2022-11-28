package com.hientran.wallpaper.data.di

import android.content.Context
import androidx.room.Room
import com.hientran.wallpaper.data.WallpaperDataSource
import com.hientran.wallpaper.data.local.WallpaperDatabase
import com.hientran.wallpaper.data.local.datasource.WallpaperLocalDataSourceImpl
import com.hientran.wallpaper.data.remote.datasource.WallpaperRemoteDataSourceImpl
import com.hientran.wallpaper.data.remote.services.PexelsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @LocalDataSource
    @Provides
    fun provideWallpaperLocalDataSource(database: WallpaperDatabase): WallpaperDataSource {
        return WallpaperLocalDataSourceImpl(database.searchDao(), database.wallpaperDao())
    }

    @Singleton
    @RemoteDataSource
    @Provides
    fun provideWallpaperRemoteDataSource(
        database: WallpaperDatabase,
        pexelService: PexelsApiService
    ): WallpaperDataSource {
        return WallpaperRemoteDataSourceImpl(database, pexelService)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataModules {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): WallpaperDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            WallpaperDatabase::class.java,
            "Wallpaper.db"
        ).build()
}
