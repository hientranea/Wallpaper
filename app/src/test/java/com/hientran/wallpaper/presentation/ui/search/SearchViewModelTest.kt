@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hientran.wallpaper.presentation.ui.search

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hientran.wallpaper.base.BaseTest
import com.hientran.wallpaper.base.MockPaging
import com.hientran.wallpaper.data.local.entities.SearchEntity
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.domain.usecase.SaveSearchQueryUseCase
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
    private val saveSearchQueryUseCase = mockk<SaveSearchQueryUseCase>(relaxed = true)
    private val response = PagingData.from(listOf(WallpaperEntity()))
    private val flowPagingData = flowOf(response)

    @Test
    fun `searches with query`() {
        val queryString = "dog"
        val queryEntity = SearchEntity(query = queryString)
        every { flowPagingData.cachedIn(any()) } returns flowPagingData
        coEvery { saveSearchQueryUseCase(SaveSearchQueryUseCase.Params(queryString)) } returns queryEntity
        coEvery { searchPhotoUseCase(SearchPhotoUseCase.Params(queryEntity)) } returns flowPagingData

        val viewModel = SearchViewModel(searchPhotoUseCase, saveSearchQueryUseCase)
        assertEquals(PagingData.empty<WallpaperEntity>(), viewModel.state.value.photos)
        viewModel.search(queryString)
        assertEquals(response, viewModel.state.value.photos)
    }
}
