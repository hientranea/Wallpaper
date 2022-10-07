package com.hientran.wallpaper.presentation.ui.home

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hientran.wallpaper.R

class HomeDecorator(private val res: Resources): RecyclerView.ItemDecoration() {

    private val space by lazy { res.getDimensionPixelSize(R.dimen.home_margin_horizontal) }
    private var firstCollection = -1

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val isCollectionType = parent.getChildViewHolder(view) is CollectionViewHolder

        if (isCollectionType) {
            if (firstCollection < 0) {
                firstCollection = position
            }
            outRect.bottom = space / 2
            if ((position - firstCollection) % 2 == 0) {
                outRect.left = space
                outRect.right = space / 2
            } else {
                outRect.left = space / 2
                outRect.right = space
            }
        }
    }
}

