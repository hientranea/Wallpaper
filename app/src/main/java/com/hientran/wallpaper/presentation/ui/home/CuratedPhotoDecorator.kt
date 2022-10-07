package com.hientran.wallpaper.presentation.ui.home

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hientran.wallpaper.R

class CuratedPhotoDecorator(private val res: Resources): RecyclerView.ItemDecoration() {

    private val horizontalMargin by lazy {
        res.getDimensionPixelSize(R.dimen.home_margin_horizontal)
    }
    private val spacingMargin by lazy {
        res.getDimensionPixelSize(R.dimen.general_spacing_item)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val count = parent.adapter?.itemCount ?: 0

        when (parent.getChildAdapterPosition(view)) {
            0 -> outRect.left = horizontalMargin
            count - 1 -> {
                outRect.left = spacingMargin
                outRect.right = horizontalMargin
            }
            else -> outRect.left = spacingMargin
        }
    }
}

