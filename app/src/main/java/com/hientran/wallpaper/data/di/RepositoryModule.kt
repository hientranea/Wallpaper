package com.hientran.wallpaper.data.di

import com.hientran.wallpaper.data.WallpaperDataSource
import com.hientran.wallpaper.data.WallpaperLocalDataSource
import com.hientran.wallpaper.data.WallpaperRemoteDataSource
import com.hientran.wallpaper.data.repositories.PexelsRepositoryImpl
import com.hientran.wallpaper.data.repositories.WallpaperRepositoryImpl
import com.hientran.wallpaper.domain.repositories.PexelsRepository
import com.hientran.wallpaper.domain.repositories.WallpaperRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providePexelsRepository(
        @LocalDataSource localDataSource: WallpaperDataSource,
        @RemoteDataSource remoteDataSource: WallpaperDataSource
    ): PexelsRepository = PexelsRepositoryImpl(
        localDataSource as WallpaperLocalDataSource,
        remoteDataSource as WallpaperRemoteDataSource
    )

    @Provides
    fun provideWallpaperRepository(
        @LocalDataSource localDataSource: WallpaperDataSource,
    ): WallpaperRepository = WallpaperRepositoryImpl(localDataSource)
}
