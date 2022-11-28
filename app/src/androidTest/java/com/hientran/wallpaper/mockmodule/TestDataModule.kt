package com.hientran.wallpaper.mockmodule

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hientran.wallpaper.data.di.DataModules
import com.hientran.wallpaper.data.local.WallpaperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModules::class]
)
class TestDataModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(): WallpaperDatabase =
        Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WallpaperDatabase::class.java
        ).build()
}
