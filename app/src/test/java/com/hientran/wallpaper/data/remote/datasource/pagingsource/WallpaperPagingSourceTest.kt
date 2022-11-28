@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.hientran.wallpaper.data.remote.datasource.pagingsource

import androidx.paging.PagingSource
import com.hientran.wallpaper.common.DEFAULT_PER_PAGE
import com.hientran.wallpaper.data.remote.model.WallpaperList
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

class WallpaperPagingSourceTest {
    private val query = "dogs"
    private val mockPhotos = listOf(WallpaperPhoto())
    private val mockResponse = WallpaperList(photos = mockPhotos)
    private val service = mockk<PexelsApiService>(relaxed = true) {
        coEvery { search(any(), any(), any()) } returns mockResponse
    }
    private val pagingSource = WallpaperPagingSource(service, query)

    @Test
    fun `loads first page correctly from paging source`() {
        val loadParams = mockk<PagingSource.LoadParams<Int>> {
            every { loadSize } returns DEFAULT_PER_PAGE
        }

        runTest {
            val response = pagingSource.load(loadParams)
            coVerify { service.search(query, DEFAULT_PER_PAGE, 1) }
            assertEquals(
                PagingSource.LoadResult.Page(
                    data = mockPhotos.map { it.toWallpaperEntity() },
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
            coVerify { service.search(query, DEFAULT_PER_PAGE, 2) }
            assertEquals(
                PagingSource.LoadResult.Page(
                    data = mockPhotos.map { it.toWallpaperEntity() },
                    prevKey = 1,
                    nextKey = 3
                ),
                response
            )
        }
    }
}
