package com.hientran.wallpaper.presentation.ui.photocollection

import androidx.paging.PagingData
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.model.WallpaperPhoto

data class PhotoCollectionViewState(
    val photos: PagingData<WallpaperEntity> = PagingData.empty(),
)
