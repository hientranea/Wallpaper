package com.hientran.wallpaper.data.remote.datasource.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hientran.wallpaper.R
import com.hientran.wallpaper.common.DEFAULT_STARTING_PAGE_INDEX
import com.hientran.wallpaper.data.remote.model.toWallpaperEntity
import com.hientran.wallpaper.data.remote.services.PexelsApiService
import com.hientran.wallpaper.presentation.ui.home.HomeItemView

/**
 * Paging source loads data directly from network using Pexel APIs
 */
class HomePagingSource(
    private val pexelsApiService: PexelsApiService
): PagingSource<Int, HomeItemView>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeItemView> {
        return try {
            val position = params.key ?: DEFAULT_STARTING_PAGE_INDEX
            val collections = pexelsApiService.featuredCollections(
                page = position, perPage = params.loadSize
            )
            val data: MutableList<HomeItemView> =
                collections.collections.map { HomeItemView.Collection(it) }.toMutableList()
            if (position == 1) {
                // Add categories title
                data.add(0, HomeItemView.Title(R.string.home_categories))

                // Fetch and add list of curated photos
                val curatedPhotos = pexelsApiService.getCuratedPhotos()
                val photos = curatedPhotos.photos.map { it.toWallpaperEntity() }
                data.add(0, HomeItemView.CuratedPhotos(photos))

                // Add curated photos title
                data.add(0, HomeItemView.Title(R.string.home_best_of_month))

                // Add search bar
                data.add(0, HomeItemView.SearchBar())
            }

            LoadResult.Page(
                data = data,
                prevKey = if (position == DEFAULT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (collections.collections.isEmpty()) null else position + 1
            )
        } catch (exception: Throwable) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HomeItemView>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
