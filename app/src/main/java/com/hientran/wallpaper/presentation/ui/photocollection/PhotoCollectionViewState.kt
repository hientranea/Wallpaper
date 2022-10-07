package com.hientran.wallpaper.presentation.ui.photocollection

import androidx.paging.PagingData
import com.hientran.wallpaper.data.model.WallpaperPhoto

data class PhotoCollectionViewState(
    val photos: PagingData<WallpaperPhoto> = PagingData.empty(),
)
