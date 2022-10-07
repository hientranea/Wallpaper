package com.hientran.wallpaper.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hientran.wallpaper.data.model.WallpaperPhoto
import com.hientran.wallpaper.databinding.ItemWallpaperBinding
import com.hientran.wallpaper.presentation.ui.common.viewholder.WallpaperViewHolder

class CuratedPhotoAdapter(
    private val onPhotoClick: (View, String) -> Unit
): ListAdapter<WallpaperPhoto, WallpaperViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWallpaperBinding.inflate(inflater, parent, false)
        val width = if (parent.width == 0) parent.measuredWidth else parent.width
        binding.root.layoutParams.width = (width * DELTA_WIDTH).toInt()
        return WallpaperViewHolder(onPhotoClick, binding)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<WallpaperPhoto>() {
            override fun areItemsTheSame(
                oldItem: WallpaperPhoto,
                newItem: WallpaperPhoto
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: WallpaperPhoto,
                newItem: WallpaperPhoto
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

const val DELTA_WIDTH = 0.35
