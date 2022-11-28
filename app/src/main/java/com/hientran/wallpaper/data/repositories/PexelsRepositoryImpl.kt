package com.hientran.wallpaper.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hientran.wallpaper.common.DEFAULT_PER_PAGE
import com.hientran.wallpaper.data.WallpaperLocalDataSource
import com.hientran.wallpaper.data.WallpaperRemoteDataSource
import com.hientran.wallpaper.data.di.LocalDataSource
import com.hientran.wallpaper.data.di.RemoteDataSource
import com.hientran.wallpaper.data.local.entities.SearchEntity
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.domain.repositories.PexelsRepository
import com.hientran.wallpaper.presentation.ui.home.HomeItemView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PexelsRepositoryImpl @Inject constructor(
    @LocalDataSource private val localDS: WallpaperLocalDataSource,
    @RemoteDataSource private val remoteDS: WallpaperRemoteDataSource
): PexelsRepository {
    private val generalPagingConfig = PagingConfig(
        initialLoadSize = DEFAULT_PER_PAGE,
        pageSize = DEFAULT_PER_PAGE,
        enablePlaceholders = false
    )

    @OptIn(ExperimentalPagingApi::class)
    override fun search(keyword: SearchEntity): Flow<PagingData<WallpaperEntity>> {
        return Pager(
            config = generalPagingConfig,
            remoteMediator = remoteDS.getPhotoByQueryMediator(keyword),
            pagingSourceFactory = {
                localDS.getPhotoByQueryPagingSource(keyword.query)
            }
        ).flow
    }

    override fun getHomeData(): Flow<PagingData<HomeItemView>> {
        return Pager(
            config = generalPagingConfig,
            pagingSourceFactory = { remoteDS.getHomePagingSource() }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getCollectionMedia(id: String): Flow<PagingData<WallpaperEntity>> {
        return Pager(
            config = generalPagingConfig,
            remoteMediator = remoteDS.getPhotoByCollectionMediator(id),
            pagingSourceFactory = { localDS.getPhotoByCollectionPagingSource(id) }
        ).flow
    }
}
