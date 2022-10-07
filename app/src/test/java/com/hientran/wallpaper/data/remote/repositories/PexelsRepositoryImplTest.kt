@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hientran.wallpaper.data.remote.repositories

import androidx.paging.map
import com.hientran.wallpaper.data.model.WallpaperList
import com.hientran.wallpaper.data.model.WallpaperPhoto
import com.hientran.wallpaper.data.remote.pagingsource.WallpaperPagingSource
import com.hientran.wallpaper.data.remote.services.PexelsApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class PexelsRepositoryImplTest {
    private val mockPhotos = listOf(WallpaperPhoto())
    private val mockResponse = WallpaperList(photos = mockPhotos)
    private val service = mockk<PexelsApiService>(relaxed = true) {
        coEvery { search(any(), any(), any()) } returns mockResponse
    }
    private val repo = PexelsRepositoryImpl(service)

    @Test
    fun `search photos`() = runTest {
        val photos = mutableListOf<WallpaperPhoto>()
        val response = repo.search("a")
        response.javaClass


        assertEquals(1, photos.size)
    }
}
