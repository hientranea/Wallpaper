package com.hientran.wallpaper.presentation.ui.home

import androidx.annotation.StringRes
import com.hientran.wallpaper.data.model.PhotoCollection
import com.hientran.wallpaper.data.model.WallpaperPhoto

sealed class HomeItemView {
    data class SearchBar(var value: String = ""): HomeItemView()

    data class Title(@StringRes val stringId: Int): HomeItemView()

    data class CuratedPhotos(val photos: List<WallpaperPhoto>): HomeItemView()

    data class Collection(val data: PhotoCollection): HomeItemView()
}
