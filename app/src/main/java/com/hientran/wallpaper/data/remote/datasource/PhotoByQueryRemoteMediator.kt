package com.hientran.wallpaper.data.remote.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.hientran.wallpaper.data.local.WallpaperDatabase
import com.hientran.wallpaper.data.local.entities.RemoteKeyEntity
import com.hientran.wallpaper.data.local.entities.SearchEntity
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.model.toWallpaperEntity
import com.hientran.wallpaper.data.remote.services.PexelsApiService

@OptIn(ExperimentalPagingApi::class)
class PhotoByQueryRemoteMediator(
    private val database: WallpaperDatabase,
    private val pexelService: PexelsApiService,
    private val searchEntity: SearchEntity
): RemoteMediator<Int, WallpaperEntity>() {
    private val photoDao = database.wallpaperDao()
    private val remoteKeyDao = database.remoteKeyDao()
    private val remoteKeyLabel = SEARCH_PHOTO_PREFIX + searchEntity.query

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WallpaperEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                REFRESH -> null
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getKeys(remoteKeyLabel).firstOrNull()
                    }
                    // If remote key exist, we need to check the latest page has data or not
                    if (remoteKey != null && remoteKey.nLastItems == 0) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey
                }
            }

            val nextPage = loadKey?.nextPage ?: 1
            val items = pexelService.search(
                query = searchEntity.query,
                perPage = state.config.pageSize,
                page = nextPage
            ).photos.map { it.toWallpaperEntity(searchId = searchEntity.id) }

            database.withTransaction {
                if (loadType == REFRESH) {
                    photoDao.clearAllBySearchQuery(searchEntity.id)
                    remoteKeyDao.clearKeyWithLabel(remoteKeyLabel)
                }

                remoteKeyDao.saveNewKey(
                    RemoteKeyEntity(
                        label = remoteKeyLabel,
                        nextPage = nextPage + 1,
                        nLastItems = items.size
                    )
                )
                photoDao.insertAll(items)
            }
            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    companion object {
        const val SEARCH_PHOTO_PREFIX = "search_"
    }
}
