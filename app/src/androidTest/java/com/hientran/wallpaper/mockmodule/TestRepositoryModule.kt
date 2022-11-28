package com.hientran.wallpaper.mockmodule

import com.hientran.wallpaper.data.WallpaperDataSource
import com.hientran.wallpaper.data.di.LocalDataSource
import com.hientran.wallpaper.data.di.RemoteDataSource
import com.hientran.wallpaper.data.di.RepositoryModule
import com.hientran.wallpaper.data.repositories.WallpaperRepositoryImpl
import com.hientran.wallpaper.domain.repositories.PexelsRepository
import com.hientran.wallpaper.domain.repositories.WallpaperRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class TestRepositoryModule {
    @Provides
    fun providePexelsRepository(
        @LocalDataSource localDataSource: WallpaperDataSource,
        @RemoteDataSource remoteDataSource: WallpaperDataSource
    ): PexelsRepository = FakePexelRepositoryImpl()

    @Provides
    fun provideWallpaperRepository(
        @LocalDataSource localDataSource: WallpaperDataSource,
    ): WallpaperRepository = WallpaperRepositoryImpl(localDataSource)
}
