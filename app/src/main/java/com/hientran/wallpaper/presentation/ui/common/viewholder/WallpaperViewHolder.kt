package com.hientran.wallpaper.presentation.ui.common.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hientran.wallpaper.common.extensions.loadWithGlide
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.databinding.ItemWallpaperBinding

class WallpaperViewHolder(
    private val onPhotoClick: (View, String) -> Unit,
    private val bindingView: ItemWallpaperBinding
): RecyclerView.ViewHolder(bindingView.root) {

    fun bind(photo: WallpaperEntity) {
        with(bindingView.ivItemWallpaperThumbnail) {
            loadWithGlide(photo.mediumUrl)
            setOnClickListener { view -> onPhotoClick(view, photo.portraitUrl) }
        }
    }
}
