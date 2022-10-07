@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hientran.wallpaper.presentation.ui.home

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hientran.wallpaper.base.BaseTest
import com.hientran.wallpaper.base.MockPaging
import com.hientran.wallpaper.domain.usecase.GetHomeDataUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeViewModelTest: BaseTest(), MockPaging {
    private val getHomeDataUseCase = mockk<GetHomeDataUseCase>(relaxed = true)
    private val response = PagingData.from<HomeItemView>(listOf(HomeItemView.SearchBar()))
    private val flowPagingData = flowOf(response)

    @Test
    fun `fetch home data`() {
        every { flowPagingData.cachedIn(any()) } returns flowPagingData
        coEvery { getHomeDataUseCase(any()) } returns flowPagingData

        val viewModel = HomeViewModel(getHomeDataUseCase)
        assertEquals(response, viewModel.state.value.homeItemViews)
    }
}
