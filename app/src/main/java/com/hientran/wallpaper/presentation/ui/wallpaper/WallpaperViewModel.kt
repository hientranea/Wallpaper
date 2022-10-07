package com.hientran.wallpaper.presentation.ui.wallpaper

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.hientran.wallpaper.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel @Inject constructor(): BaseViewModel() {
    private val _state = MutableStateFlow(WallpaperViewState())
    val state: StateFlow<WallpaperViewState> = _state

    fun calcNewBitmapSize(
        bitmapWidth: Int, bitmapHeight: Int, screenWidth: Int, screenHeight: Int
    ): Pair<Int, Int> {
        val bitmapRatio = (bitmapWidth.toFloat() / bitmapHeight)
        val screenRatio = (screenWidth.toFloat() / screenHeight)
        val newBitmapWidth: Int
        val newBitmapHeight: Int
        if (screenRatio > bitmapRatio) {
            newBitmapWidth = screenWidth
            newBitmapHeight = (newBitmapWidth / bitmapRatio).toInt()
        } else {
            newBitmapHeight = screenHeight
            newBitmapWidth = (newBitmapHeight * bitmapRatio).toInt()
        }
        return Pair(newBitmapWidth, newBitmapHeight)
    }

    fun calcBitmapGap(
        bitmapWidth: Int, bitmapHeight: Int, screenWidth: Int, screenHeight: Int
    ): Pair<Int, Int> {
        val bitmapGapX = ((bitmapWidth - screenWidth.toFloat()) / 2.0f).toInt()
        val bitmapGapY = ((bitmapHeight - screenHeight.toFloat()) / 2.0f).toInt()
        return Pair(bitmapGapX, bitmapGapY)
    }

    fun cropBitmapFromCenterAndScreenSize(
        bitmap: Bitmap,
        screenWidth: Int,
        screenHeight: Int
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val (newBitmapWidth, newBitmapHeight) = calcNewBitmapSize(
                    bitmap.width, bitmap.height, screenWidth, screenHeight
                )
                val (bitmapGapX, bitmapGapY) = calcBitmapGap(
                    newBitmapWidth, newBitmapHeight, screenWidth, screenHeight
                )
                var croppedBitmap =
                    Bitmap.createScaledBitmap(bitmap, newBitmapWidth, newBitmapHeight, true)
                croppedBitmap = Bitmap.createBitmap(
                    croppedBitmap, bitmapGapX, bitmapGapY, screenWidth, screenHeight
                )
                _state.update { it.copy(processedBitmap = croppedBitmap) }
            }
        }
    }
}
