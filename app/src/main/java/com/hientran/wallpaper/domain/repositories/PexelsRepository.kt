package com.hientran.wallpaper.domain.repositories

import androidx.paging.PagingData
import com.hientran.wallpaper.data.model.WallpaperPhoto
import com.hientran.wallpaper.presentation.ui.home.HomeItemView
import kotlinx.coroutines.flow.Flow

interface PexelsRepository {
    fun search(keyword: String): Flow<PagingData<WallpaperPhoto>>

    fun getHomeData(): Flow<PagingData<HomeItemView>>

    fun getCollectionMedia(id: String): Flow<PagingData<WallpaperPhoto>>
}
