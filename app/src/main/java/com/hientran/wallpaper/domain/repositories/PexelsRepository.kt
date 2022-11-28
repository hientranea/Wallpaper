package com.hientran.wallpaper.domain.repositories

import androidx.paging.PagingData
import com.hientran.wallpaper.data.local.entities.SearchEntity
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.model.WallpaperPhoto
import com.hientran.wallpaper.presentation.ui.home.HomeItemView
import kotlinx.coroutines.flow.Flow

interface PexelsRepository {
    fun search(keyword: SearchEntity): Flow<PagingData<WallpaperEntity>>

    fun getHomeData(): Flow<PagingData<HomeItemView>>

    fun getCollectionMedia(id: String): Flow<PagingData<WallpaperEntity>>
}
