package com.hientran.wallpaper.presentation.ui.home

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.hientran.wallpaper.R
import com.hientran.wallpaper.data.model.PhotoCollection
import com.hientran.wallpaper.databinding.ItemCollectionBinding
import com.hientran.wallpaper.databinding.ItemHomeCuratedPhotosBinding
import com.hientran.wallpaper.databinding.ItemHomeSearchBarBinding
import com.hientran.wallpaper.databinding.ItemHomeTitleBinding
import com.hientran.wallpaper.presentation.ui.home.HomeItemView.CuratedPhotos
import com.hientran.wallpaper.presentation.ui.home.HomeItemView.SearchBar
import com.hientran.wallpaper.presentation.ui.home.HomeItemView.Title
import java.util.PrimitiveIterator

class HomePagingAdapter(
    private val onPhotoClick: (View, String) -> Unit,
    private val onCollectionClick: (PhotoCollection) -> Unit
): PagingDataAdapter<HomeItemView, HomeViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val searchBarView = ItemHomeSearchBarBinding.inflate(inflater, parent, false)
        val titleView = ItemHomeTitleBinding.inflate(inflater, parent, false)
        val curatedPhotosView = ItemHomeCuratedPhotosBinding.inflate(inflater, parent, false)
        val collectionView = ItemCollectionBinding.inflate(inflater, parent, false)

        val data: TypedArray = curatedPhotosView.root.resources.obtainTypedArray(R.array.bg_gradient)
        val gradientIds = mutableListOf<Int>()
        for (i in 0 until data.length()) {
            gradientIds.add(data.getResourceId(i, 0))
        }
        data.recycle()

        return when (viewType) {
            R.layout.item_home_search_bar -> HomeSearchBarViewHolder(searchBarView)
            R.layout.item_home_title -> HomeTitleViewHolder(titleView)
            R.layout.item_home_curated_photos ->
                HomeCuratedPhotosViewHolder(onPhotoClick, curatedPhotosView)
            R.layout.item_collection ->
                CollectionViewHolder(gradientIds, collectionView, onCollectionClick)
            else -> HomeTitleViewHolder(titleView)
        }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        when (val data = getItem(position)) {
            is Title -> (holder as HomeTitleViewHolder).bind(data)
            is CuratedPhotos -> (holder as HomeCuratedPhotosViewHolder).bind(data)
            is HomeItemView.Collection -> (holder as CollectionViewHolder).bind(data)
            else -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchBar -> R.layout.item_home_search_bar
            is Title -> R.layout.item_home_title
            is CuratedPhotos -> R.layout.item_home_curated_photos
            is HomeItemView.Collection -> R.layout.item_collection
            else -> -1
        }
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<HomeItemView>() {
            override fun areItemsTheSame(oldItem: HomeItemView, newItem: HomeItemView): Boolean {
                return when {
                    oldItem is SearchBar && newItem is SearchBar -> oldItem.value == newItem.value
                    oldItem is Title && newItem is Title -> oldItem.stringId == newItem.stringId
                    oldItem is HomeItemView.Collection && newItem is HomeItemView.Collection -> oldItem.data == newItem.data
                    oldItem is CuratedPhotos && newItem is CuratedPhotos -> oldItem.photos == newItem.photos
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: HomeItemView, newItem: HomeItemView): Boolean {
                return areItemsTheSame(oldItem, newItem)
            }
        }
    }
}
