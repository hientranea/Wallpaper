package com.hientran.wallpaper.presentation.ui.search

import androidx.paging.PagingData
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.model.WallpaperPhoto

data class SearchViewState(
    val photos: PagingData<WallpaperEntity> = PagingData.empty(),
)
