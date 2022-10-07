package com.hientran.wallpaper.domain.usecase

import androidx.paging.PagingData
import com.hientran.wallpaper.data.model.WallpaperPhoto
import com.hientran.wallpaper.domain.repositories.PexelsRepository
import com.hientran.wallpaper.domain.usecase.base.FlowUseCase
import com.hientran.wallpaper.presentation.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCollectionMediaUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val pexelsRepository: PexelsRepository,
): FlowUseCase<GetCollectionMediaUseCase.Params, PagingData<WallpaperPhoto>>(ioDispatcher) {

    override fun execute(params: Params): Flow<PagingData<WallpaperPhoto>> {
        return pexelsRepository.getCollectionMedia(params.id)
    }

    data class Params(val id: String)
}

