@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hientran.wallpaper.presentation.ui.search

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hientran.wallpaper.base.BaseTest
import com.hientran.wallpaper.base.MockPaging
import com.hientran.wallpaper.data.model.WallpaperPhoto
import com.hientran.wallpaper.domain.usecase.SearchPhotoUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchViewModelTest: BaseTest(), MockPaging {
    private val searchPhotoUseCase = mockk<SearchPhotoUseCase>(relaxed = true)
    private val response = PagingData.from(listOf(WallpaperPhoto()))
    private val flowPagingData = flowOf(response)

    @Test
    fun `searches with query`() {
        every { flowPagingData.cachedIn(any()) } returns flowPagingData
        coEvery { searchPhotoUseCase(SearchPhotoUseCase.Params("dog")) } returns flowPagingData

        val viewModel = SearchViewModel(searchPhotoUseCase)
        assertEquals(PagingData.empty<WallpaperPhoto>(), viewModel.state.value.photos)
        viewModel.search("dog")
        assertEquals(response, viewModel.state.value.photos)
    }
}
