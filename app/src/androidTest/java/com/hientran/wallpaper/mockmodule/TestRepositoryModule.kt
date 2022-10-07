package com.hientran.wallpaper.mockmodule

import com.hientran.wallpaper.data.di.RepositoryModule
import com.hientran.wallpaper.data.remote.services.PexelsApiService
import com.hientran.wallpaper.domain.repositories.PexelsRepository
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
    fun providePexelsRepository(pexelsApiService: PexelsApiService): PexelsRepository =
        FakePexelRepositoryImpl()
}
