package com.hientran.wallpaper.presentation.ui.common.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hientran.wallpaper.common.extensions.loadWithGlide
import com.hientran.wallpaper.data.model.WallpaperPhoto
import com.hientran.wallpaper.databinding.ItemWallpaperBinding

class WallpaperViewHolder(
    private val onPhotoClick: (View, String) -> Unit,
    private val bindingView: ItemWallpaperBinding
): RecyclerView.ViewHolder(bindingView.root) {

    fun bind(photo: WallpaperPhoto) {
        photo.urls?.let { urls ->
            with(bindingView.ivItemWallpaperThumbnail) {
                loadWithGlide(urls.medium)
                setOnClickListener { view -> onPhotoClick(view, urls.portrait) }
            }
        }
    }
}
