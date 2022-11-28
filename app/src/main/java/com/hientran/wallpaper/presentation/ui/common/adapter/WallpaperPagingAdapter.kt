package com.hientran.wallpaper.presentation.ui.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.model.WallpaperPhoto
import com.hientran.wallpaper.databinding.ItemWallpaperBinding
import com.hientran.wallpaper.presentation.ui.common.viewholder.WallpaperViewHolder

class WallpaperPagingAdapter(
    private val onPhotoClick: (View, String) -> Unit,
): PagingDataAdapter<WallpaperEntity, WallpaperViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemWallpaperBinding.inflate(inflater, parent, false)
        return WallpaperViewHolder(onPhotoClick, view)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<WallpaperEntity>() {
            override fun areItemsTheSame(
                oldItem: WallpaperEntity, newItem: WallpaperEntity
            ) = oldItem.photoId == newItem.photoId

            override fun areContentsTheSame(
                oldItem: WallpaperEntity, newItem: WallpaperEntity
            ) = oldItem.photoId == newItem.photoId
        }
    }
}
