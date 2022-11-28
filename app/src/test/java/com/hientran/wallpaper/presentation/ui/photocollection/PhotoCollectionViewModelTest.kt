@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hientran.wallpaper.presentation.ui.photocollection

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hientran.wallpaper.base.BaseTest
import com.hientran.wallpaper.base.MockPaging
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.model.WallpaperPhoto
import com.hientran.wallpaper.domain.usecase.GetCollectionMediaUseCase
import com.hientran.wallpaper.domain.usecase.GetCollectionMediaUseCase.Params
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoCollectionViewModelTest: BaseTest(), MockPaging {
    private val getCollectionMediaUseCase = mockk<GetCollectionMediaUseCase>(relaxed = true)
    private val response = PagingData.from(listOf(WallpaperEntity()))
    private val flowPagingData = flowOf(response)

    @Test
    fun `fetch home data`() {
        every { flowPagingData.cachedIn(any()) } returns flowPagingData
        coEvery { getCollectionMediaUseCase(Params("id")) } returns flowPagingData

        val viewModel = PhotoCollectionViewModel(getCollectionMediaUseCase)
        assertEquals(PagingData.empty<WallpaperPhoto>(), viewModel.state.value.photos)
        viewModel.getCollectionMedia("id")
        assertEquals(response, viewModel.state.value.photos)
    }
}

