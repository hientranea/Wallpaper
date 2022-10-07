package com.hientran.wallpaper.presentation.ui.wallpaper

import android.app.WallpaperManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.hientran.wallpaper.R
import com.hientran.wallpaper.common.WALLPAPER_TRANS_NAME
import com.hientran.wallpaper.common.extensions.loadBitmapWithGlide
import com.hientran.wallpaper.common.extensions.loadWithGlide
import com.hientran.wallpaper.databinding.FragmentWallpaperBinding
import com.hientran.wallpaper.presentation.base.BaseFragment
import com.hientran.wallpaper.presentation.ui.common.viewBinding

class WallpaperFragment: BaseFragment(R.layout.fragment_wallpaper) {
    override val viewModel: WallpaperViewModel by viewModels()
    private val bindingView by viewBinding(FragmentWallpaperBinding::bind)
    private val args: WallpaperFragmentArgs by navArgs()
    private lateinit var wallpaperManager: WallpaperManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_image)
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wallpaperManager = WallpaperManager.getInstance(requireActivity().applicationContext)

        with(bindingView) {
            ivWallpaper.apply {
                ViewCompat.setTransitionName(this, WALLPAPER_TRANS_NAME)
                loadWithGlide(args.url, ::enterTransition, ::enterTransition)
            }
            ivBackButton.setOnClickListener { findNavController().navigateUp() }
            ivBrushIcon.setOnClickListener { setWallpaper() }
        }

        observeState {
            viewModel.state.collect { state ->
                state.processedBitmap?.let {
                    dialogUtil.hideProgressDialog()
                    wallpaperManager.setBitmap(
                        it, null, true, WallpaperManager.FLAG_SYSTEM
                    )
                    Toast.makeText(
                        requireActivity().applicationContext,
                        getString(R.string.apply_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setWallpaper() {
        dialogUtil.showProgressDialog(view)
        requireActivity().loadBitmapWithGlide(args.url,
            onLoadFailed = { dialogUtil.hideProgressDialog() },
            onResourceReady = { bitmap ->
                bitmap?.let {
                    val metrics = DisplayMetrics()
                    requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
                    viewModel.cropBitmapFromCenterAndScreenSize(
                        it,
                        metrics.widthPixels,
                        metrics.heightPixels
                    )
                }
            }
        )
    }

    private fun enterTransition() {
        startPostponedEnterTransition()
    }
}
