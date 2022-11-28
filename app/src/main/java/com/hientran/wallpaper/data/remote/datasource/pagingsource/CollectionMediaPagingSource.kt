package com.hientran.wallpaper.data.remote.datasource.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hientran.wallpaper.common.DEFAULT_STARTING_PAGE_INDEX
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.model.toWallpaperEntity
import com.hientran.wallpaper.data.remote.services.PexelsApiService

/**
 * Paging source loads data directly from network using Pexel APIs
 */
class CollectionMediaPagingSource(
    private val pexelsApiService: PexelsApiService,
    private val collectionId: String
): PagingSource<Int, WallpaperEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WallpaperEntity> {
        return try {
            val position = params.key ?: DEFAULT_STARTING_PAGE_INDEX
            val mediaData = pexelsApiService.getCollectionMedia(
                id = collectionId,
                page = position, perPage = params.loadSize
            )
            LoadResult.Page(
                data = mediaData.photos.map { it.toWallpaperEntity(collectionId) },
                prevKey = if (position == DEFAULT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (mediaData.photos.isEmpty()) null else position + 1
            )
        } catch (exception: Throwable) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, WallpaperEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
