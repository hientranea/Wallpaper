package com.hientran.wallpaper

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hientran.wallpaper.data.local.WallpaperDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
abstract class BaseDatabaseTest {
    abstract fun getHiltRule(): HiltAndroidRule

    @Inject
    lateinit var database: WallpaperDatabase

    @Before
    fun init() {
        getHiltRule().inject()
    }

    @After
    fun tearDown() {
        database.close()
    }
}

