package com.hientran.wallpaper.presentation.ui.photocollection

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.hientran.wallpaper.R
import com.hientran.wallpaper.common.WALLPAPER_TRANS_NAME
import com.hientran.wallpaper.databinding.FragmentPhotocollectionBinding
import com.hientran.wallpaper.presentation.base.BaseFragment
import com.hientran.wallpaper.presentation.ui.common.adapter.GeneralLoadStateAdapter
import com.hientran.wallpaper.presentation.ui.common.adapter.WallpaperPagingAdapter
import com.hientran.wallpaper.presentation.ui.common.viewBinding
import com.hientran.wallpaper.presentation.ui.photocollection.PhotoCollectionFragmentDirections.Companion.actionPhotoCollectionFragmentToWallpaperFragment
import kotlinx.coroutines.launch

class PhotoCollectionFragment: BaseFragment(R.layout.fragment_photocollection) {
    private val bindingView by viewBinding(FragmentPhotocollectionBinding::bind)
    override val viewModel: PhotoCollectionViewModel by viewModels()
    private val args: PhotoCollectionFragmentArgs by navArgs()
    private lateinit var pagingAdapter: WallpaperPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCollectionMedia(args.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeUiState()
    }

    private fun initViews() {
        pagingAdapter = WallpaperPagingAdapter { photoView, url ->
            ViewCompat.setTransitionName(photoView, WALLPAPER_TRANS_NAME)
            val extras = FragmentNavigatorExtras(photoView to WALLPAPER_TRANS_NAME)
            findNavController().navigate(
                actionPhotoCollectionFragmentToWallpaperFragment(url), extras
            )
        }
        val footerAdapter = GeneralLoadStateAdapter { pagingAdapter.retry() }
        with(bindingView) {
            tvCollectionTitle.text = args.name
            tvCollectionDesc.text = getString(R.string.collection_description, args.nPhotos)
            rvPhotoCollection.apply {
                adapter = pagingAdapter.withLoadStateFooter(footerAdapter)
                layoutManager = GridLayoutManager(context, 2)
                addItemDecoration(PhotoCollectionDecorator(resources, 2))
            }
            tbPhotoCollection.navigationIcon =
                getDrawable(resources, R.drawable.ic_black_back, null)
            tbPhotoCollection.setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { uiState ->
                    pagingAdapter.submitData(lifecycle, uiState.photos)
                }
            }
        }
    }
}
