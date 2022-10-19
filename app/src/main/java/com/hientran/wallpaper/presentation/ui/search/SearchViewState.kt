package com.hientran.wallpaper.presentation.ui.search

import androidx.paging.PagingData
import com.hientran.wallpaper.data.model.WallpaperPhoto

data class SearchViewState(
    val photos: PagingData<WallpaperPhoto> = PagingData.empty(),
)
