@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hientran.wallpaper.data.remote.datasource.pagingsource

import androidx.paging.PagingSource
import com.hientran.wallpaper.common.DEFAULT_PER_PAGE
import com.hientran.wallpaper.data.remote.model.CollectionMedia
import com.hientran.wallpaper.data.remote.model.WallpaperPhoto
import com.hientran.wallpaper.data.remote.model.toWallpaperEntity
import com.hientran.wallpaper.data.remote.services.PexelsApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CollectionMediaPagingSourceTest {
    private val collectionId = "abcXyz"
    private val mockPhotos = listOf(WallpaperPhoto())
    private val mockResponse = CollectionMedia(photos = mockPhotos)
    private val service = mockk<PexelsApiService>(relaxed = true) {
        coEvery { getCollectionMedia(any(), any(), any(), any()) } returns mockResponse
    }
    private val pagingSource = CollectionMediaPagingSource(service, collectionId)

    @Test
    fun `loads first page correctly from paging source`() {
        val loadParams = mockk<PagingSource.LoadParams<Int>> {
            every { loadSize } returns DEFAULT_PER_PAGE
        }

        runTest {
            val response = pagingSource.load(loadParams)
            coVerify { service.getCollectionMedia(collectionId, DEFAULT_PER_PAGE, 1, "photos") }
            assertEquals(
                PagingSource.LoadResult.Page(
                    data = mockPhotos.map { it.toWallpaperEntity(collectionId) },
                    prevKey = null,
                    nextKey = 2
                ),
                response
            )
        }
    }

    @Test
    fun `loads second page correctly from paging source`() {
        val loadParams = mockk<PagingSource.LoadParams.Append<Int>> {
            every { key } returns 2
            every { loadSize } returns DEFAULT_PER_PAGE
        }
        runTest {
            val response = pagingSource.load(loadParams)
            coVerify { service.getCollectionMedia(collectionId, DEFAULT_PER_PAGE, 2, "photos") }
            assertEquals(
                PagingSource.LoadResult.Page(
                    data = mockPhotos.map { it.toWallpaperEntity(collectionId) },
                    prevKey = 1,
                    nextKey = 3
                ),
                response
            )
        }
    }
}
