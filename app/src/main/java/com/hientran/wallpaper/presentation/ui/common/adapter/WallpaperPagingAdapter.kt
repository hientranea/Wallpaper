package com.hientran.wallpaper.presentation.ui.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.hientran.wallpaper.data.model.WallpaperPhoto
import com.hientran.wallpaper.databinding.ItemWallpaperBinding
import com.hientran.wallpaper.presentation.ui.common.viewholder.WallpaperViewHolder

class WallpaperPagingAdapter(
    private val onPhotoClick: (View, String) -> Unit,
): PagingDataAdapter<WallpaperPhoto, WallpaperViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemWallpaperBinding.inflate(inflater, parent, false)
        return WallpaperViewHolder(onPhotoClick, view)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<WallpaperPhoto>() {
            override fun areItemsTheSame(
                oldItem: WallpaperPhoto, newItem: WallpaperPhoto
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: WallpaperPhoto, newItem: WallpaperPhoto
            ) = oldItem.id == newItem.id
        }
    }
}
