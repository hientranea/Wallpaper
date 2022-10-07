package com.hientran.wallpaper.presentation.ui.wallpaper

import org.junit.Assert.assertEquals
import org.junit.Test

class WallpaperViewModelTest {
    private val viewModel = WallpaperViewModel()

    @Test
    fun `calculates new bitmap size in case bitmap and screen has the same ratio`() {
        val (newWidth, newHeight) = viewModel.calcNewBitmapSize(600, 800, 300, 400)
        assertEquals(300, newWidth)
        assertEquals(400, newHeight)
    }

    @Test
    fun `calculates new bitmap size in case bitmap ratio is greater than screen ratio`() {
        val (newWidth, newHeight) = viewModel.calcNewBitmapSize(700, 800, 300, 400)
        assertEquals(350, newWidth)
        assertEquals(400, newHeight)
    }

    @Test
    fun `calculates new bitmap size in case bitmap ratio is less than screen ratio`() {
        val (newWidth, newHeight) = viewModel.calcNewBitmapSize(500, 800, 300, 400)
        assertEquals(300, newWidth)
        assertEquals(480, newHeight)
    }

    @Test
    fun `calculate bitmap gap`() {
        val (gapX, gapY) = viewModel.calcBitmapGap(300, 400, 600, 800)
        assertEquals(-150, gapX)
        assertEquals(-200, gapY)

        val (gapX1, gapY1) = viewModel.calcBitmapGap(700, 840, 600, 800)
        assertEquals(50, gapX1)
        assertEquals(20, gapY1)
    }
}
