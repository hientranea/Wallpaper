package com.hientran.wallpaper.data.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hientran.wallpaper.common.DEFAULT_STARTING_PAGE_INDEX
import com.hientran.wallpaper.data.model.WallpaperList
import com.hientran.wallpaper.data.model.WallpaperPhoto
import com.hientran.wallpaper.data.remote.services.PexelsApiService

class WallpaperPagingSource(
    private val pexelsApiService: PexelsApiService,
    private val query: String
): PagingSource<Int, WallpaperPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WallpaperPhoto> {
        return try {
            val position = params.key ?: DEFAULT_STARTING_PAGE_INDEX
            val data: WallpaperList = pexelsApiService.search(
                page = position,
                perPage = params.loadSize,
                query = query
            )
            LoadResult.Page(
                data = data.photos,
                prevKey = if (position == DEFAULT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (data.photos.isEmpty()) null else position + 1
            )
        } catch (exception: Throwable) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, WallpaperPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
