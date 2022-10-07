package com.hientran.wallpaper.presentation.ui.photocollection

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hientran.wallpaper.R

class PhotoCollectionDecorator(
    private val res: Resources,
    private val spanCount: Int
): RecyclerView.ItemDecoration() {
    private val space by lazy { res.getDimensionPixelSize(R.dimen.home_margin_horizontal) }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount;
        outRect.left = space - column * space / spanCount
        outRect.right = (column + 1) * space / spanCount
        outRect.bottom = space

        if (position < spanCount) {
            outRect.top = space
        }
    }
}
