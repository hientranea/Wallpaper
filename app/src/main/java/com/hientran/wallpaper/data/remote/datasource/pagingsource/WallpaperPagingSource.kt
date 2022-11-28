package com.hientran.wallpaper.data.remote.datasource.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hientran.wallpaper.common.DEFAULT_STARTING_PAGE_INDEX
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.model.WallpaperList
import com.hientran.wallpaper.data.remote.model.toWallpaperEntity
import com.hientran.wallpaper.data.remote.services.PexelsApiService

/**
 * Paging source loads data directly from network using Pexel APIs
 */
class WallpaperPagingSource(
    private val pexelsApiService: PexelsApiService,
    private val query: String
): PagingSource<Int, WallpaperEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WallpaperEntity> {
        return try {
            val position = params.key ?: DEFAULT_STARTING_PAGE_INDEX
            val data: WallpaperList = pexelsApiService.search(
                page = position,
                perPage = params.loadSize,
                query = query
            )
            LoadResult.Page(
                data = data.photos.map { it.toWallpaperEntity() },
                prevKey = if (position == DEFAULT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (data.photos.isEmpty()) null else position + 1
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
