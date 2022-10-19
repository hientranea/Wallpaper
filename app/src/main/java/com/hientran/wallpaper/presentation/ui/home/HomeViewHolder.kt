package com.hientran.wallpaper.presentation.ui.home

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.hientran.wallpaper.data.model.PhotoCollection
import com.hientran.wallpaper.databinding.ItemCollectionBinding
import com.hientran.wallpaper.databinding.ItemHomeCuratedPhotosBinding
import com.hientran.wallpaper.databinding.ItemHomeSearchBarBinding
import com.hientran.wallpaper.databinding.ItemHomeTitleBinding
import com.hientran.wallpaper.presentation.ui.home.HomeItemView.CuratedPhotos
import com.hientran.wallpaper.presentation.ui.home.HomeItemView.Title


abstract class HomeViewHolder(view: View): RecyclerView.ViewHolder(view)

class HomeSearchBarViewHolder(
    view: ItemHomeSearchBarBinding,
    onSearchClick: (String) -> Unit
): HomeViewHolder(view.root) {
    init {
        view.etHomeSearch.addTextChangedListener {
            view.ibHomeSearch.visibility = if (it.toString().isBlank()) View.GONE else View.VISIBLE
        }
        view.ibHomeSearch.setOnClickListener {
            val query = view.etHomeSearch.text.toString()
            onSearchClick(query)
        }
    }
}

class HomeTitleViewHolder(private val view: ItemHomeTitleBinding): HomeViewHolder(view.root) {

    fun bind(data: Title) {
        view.tvHomeTitle.text = view.root.context.getString(data.stringId)
    }
}

class HomeCuratedPhotosViewHolder(
    private val onPhotoClick: (View, String) -> Unit,
    private val view: ItemHomeCuratedPhotosBinding
): HomeViewHolder(view.root) {

    init {
        val decorator = CuratedPhotoDecorator(view.root.resources)
        view.rvHomeCuratedFeatured.addItemDecoration(decorator)
    }

    fun bind(data: CuratedPhotos) {
        CuratedPhotoAdapter(onPhotoClick).apply {
            view.rvHomeCuratedFeatured.adapter = this

            submitList(data.photos)
        }
    }
}

class CollectionViewHolder(
    private val gradientIds: List<Int>,
    private val view: ItemCollectionBinding,
    private val onCollectionClick: (PhotoCollection) -> Unit
): HomeViewHolder(view.root) {
    fun bind(data: HomeItemView.Collection) {
        view.tvItemCollection.text = data.data.title
        val gradientResId = gradientIds[curGradientIdx++ % gradientIds.size]
        view.ivItemCollectionThumbnail.apply {
            setBackgroundResource(gradientResId)
            setOnClickListener {
                onCollectionClick(data.data)
            }
        }
    }

    companion object {
        private var curGradientIdx = 0
    }
}
