package com.hientran.wallpaper.domain.usecase

import androidx.paging.PagingData
import com.hientran.wallpaper.domain.repositories.PexelsRepository
import com.hientran.wallpaper.domain.usecase.base.FlowUseCase
import com.hientran.wallpaper.presentation.di.IoDispatcher
import com.hientran.wallpaper.presentation.ui.home.HomeItemView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHomeDataUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val pexelsRepository: PexelsRepository,
): FlowUseCase<GetHomeDataUseCase.Params, PagingData<HomeItemView>>(ioDispatcher) {

    override fun execute(params: Params): Flow<PagingData<HomeItemView>> {
        return pexelsRepository.getHomeData()
    }

    object Params
}

