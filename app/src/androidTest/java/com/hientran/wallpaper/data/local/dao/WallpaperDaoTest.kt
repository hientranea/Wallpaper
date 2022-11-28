package com.hientran.wallpaper.data.local.dao

import com.hientran.wallpaper.BaseDatabaseTest
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class WallpaperDaoTest: BaseDatabaseTest() {
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    override fun getHiltRule() = hiltAndroidRule

    @Test
    fun insertAndClear(): Unit = runBlocking {
        val dao = database.wallpaperDao()

        dao.insertAll(
            listOf(
                WallpaperEntity(photoId = 1, collectionId = "dog"),
                WallpaperEntity(photoId = 2, collectionId = "dog"),
                WallpaperEntity(photoId = 3, searchId = 1000)
            )
        )
        var entities = dao.getWallpaperPhotos()
        assertEquals(3, entities.size)

        // Clear all photos links with dog collection
        dao.clearAllByCollection("dog")
        entities = dao.getWallpaperPhotos()
        assertEquals(1, entities.size)
        assertEquals(3, entities.first().photoId)

        // Clear all photos links with search id 1000
        dao.clearAllBySearchQuery(1000)
        entities = dao.getWallpaperPhotos()
        assertEquals(0, entities.size)
    }
}
