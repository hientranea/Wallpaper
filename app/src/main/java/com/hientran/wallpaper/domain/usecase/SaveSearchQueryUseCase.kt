package com.hientran.wallpaper.domain.usecase

import com.hientran.wallpaper.data.local.entities.SearchEntity
import com.hientran.wallpaper.domain.repositories.WallpaperRepository
import com.hientran.wallpaper.domain.usecase.base.SuspendUseCase
import com.hientran.wallpaper.presentation.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveSearchQueryUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val wallpaperRepository: WallpaperRepository,
): SuspendUseCase<SaveSearchQueryUseCase.Params, SearchEntity>(ioDispatcher) {

    override suspend fun execute(params: Params): SearchEntity {
        return wallpaperRepository.saveSearchQuery(params.query)
    }

    data class Params(val query: String)
}
