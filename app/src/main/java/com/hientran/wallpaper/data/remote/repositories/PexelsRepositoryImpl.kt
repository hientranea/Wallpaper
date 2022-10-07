package com.hientran.wallpaper.data.remote.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hientran.wallpaper.common.DEFAULT_PER_PAGE
import com.hientran.wallpaper.data.model.WallpaperPhoto
import com.hientran.wallpaper.data.remote.pagingsource.CollectionMediaPagingSource
import com.hientran.wallpaper.data.remote.pagingsource.HomePagingSource
import com.hientran.wallpaper.data.remote.pagingsource.WallpaperPagingSource
import com.hientran.wallpaper.data.remote.services.PexelsApiService
import com.hientran.wallpaper.domain.repositories.PexelsRepository
import com.hientran.wallpaper.presentation.ui.home.HomeItemView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PexelsRepositoryImpl @Inject constructor(
    private val pexelsService: PexelsApiService
): PexelsRepository {
    private val generalPagingConfig = PagingConfig(
        initialLoadSize = DEFAULT_PER_PAGE,
        pageSize = DEFAULT_PER_PAGE,
        enablePlaceholders = false
    )

    override fun search(keyword: String): Flow<PagingData<WallpaperPhoto>> {
        return Pager(
            config = generalPagingConfig,
            pagingSourceFactory = { WallpaperPagingSource(pexelsService, keyword) }
        ).flow
    }

    override fun getHomeData(): Flow<PagingData<HomeItemView>> {
        return Pager(
            config = generalPagingConfig,
            pagingSourceFactory = { HomePagingSource(pexelsService) }
        ).flow
    }

    override fun getCollectionMedia(id: String): Flow<PagingData<WallpaperPhoto>> {
        return Pager(
            config = generalPagingConfig,
            pagingSourceFactory = { CollectionMediaPagingSource(pexelsService, id) }
        ).flow
    }
}
