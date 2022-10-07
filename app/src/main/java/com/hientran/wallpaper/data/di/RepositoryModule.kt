package com.hientran.wallpaper.data.di

import com.hientran.wallpaper.data.remote.repositories.PexelsRepositoryImpl
import com.hientran.wallpaper.data.remote.services.PexelsApiService
import com.hientran.wallpaper.domain.repositories.PexelsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providePexelsRepository(pexelsApiService: PexelsApiService): PexelsRepository =
        PexelsRepositoryImpl(pexelsApiService)
}
