@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hientran.wallpaper.data.remote.datasource.pagingsource

import androidx.paging.PagingSource
import com.hientran.wallpaper.R
import com.hientran.wallpaper.common.DEFAULT_PER_PAGE
import com.hientran.wallpaper.data.remote.model.CollectionList
import com.hientran.wallpaper.data.remote.model.PhotoCollection
import com.hientran.wallpaper.data.remote.model.WallpaperList
import com.hientran.wallpaper.data.remote.model.toWallpaperEntity
import com.hientran.wallpaper.data.remote.services.PexelsApiService
import com.hientran.wallpaper.presentation.ui.home.HomeItemView
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class HomePagingSourceTest {
    private val mockCollections = listOf(PhotoCollection())
    private val mockCollectionList = CollectionList(collections = mockCollections)
    private val mockWallpaperList = WallpaperList()
    private val service = mockk<PexelsApiService>(relaxed = true) {
        coEvery { featuredCollections(any(), any()) } returns mockCollectionList
        coEvery { getCuratedPhotos() } returns mockWallpaperList
    }
    private val pagingSource = HomePagingSource(service)

    @Test
    fun `loads first page correctly from paging source`() {
        val loadParams = mockk<PagingSource.LoadParams<Int>> {
            every { loadSize } returns DEFAULT_PER_PAGE
        }

        runTest {
            val response = pagingSource.load(loadParams)
            coVerify { service.featuredCollections(perPage = DEFAULT_PER_PAGE, page = 1) }
            assertEquals(
                PagingSource.LoadResult.Page(
                    data = listOf(
                        HomeItemView.SearchBar(),
                        HomeItemView.Title(R.string.home_best_of_month),
                        HomeItemView.CuratedPhotos(mockWallpaperList.photos.map { it.toWallpaperEntity() }),
                        HomeItemView.Title(R.string.home_categories),
                        HomeItemView.Collection(mockCollections[0])
                    ), prevKey = null, nextKey = 2
                ), response
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
            coVerify { service.featuredCollections(perPage = DEFAULT_PER_PAGE, page = 2) }
            assertEquals(
                PagingSource.LoadResult.Page(
                    data = listOf(
                        HomeItemView.Collection(mockCollections[0])
                    ), prevKey = 1, nextKey = 3
                ), response
            )
        }
    }
}
