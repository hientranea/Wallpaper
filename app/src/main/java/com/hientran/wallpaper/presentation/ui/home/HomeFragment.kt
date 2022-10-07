package com.hientran.wallpaper.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hientran.wallpaper.R
import com.hientran.wallpaper.common.WALLPAPER_TRANS_NAME
import com.hientran.wallpaper.databinding.FragmentHomeBinding
import com.hientran.wallpaper.presentation.base.BaseFragment
import com.hientran.wallpaper.presentation.ui.common.adapter.GeneralLoadStateAdapter
import com.hientran.wallpaper.presentation.ui.common.viewBinding
import com.hientran.wallpaper.presentation.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToPhotoCollectionFragment
import com.hientran.wallpaper.presentation.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToWallpaperFragment
import kotlinx.coroutines.launch

class HomeFragment: BaseFragment(R.layout.fragment_home) {
    private val bindingView by viewBinding(FragmentHomeBinding::bind)
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomePagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeUiState()
    }

    private fun initViews() {
        homeAdapter = HomePagingAdapter(
            onPhotoClick = { photoView, url ->
                ViewCompat.setTransitionName(photoView, WALLPAPER_TRANS_NAME)
                val extras = FragmentNavigatorExtras(photoView to WALLPAPER_TRANS_NAME)
                findNavController().navigate(actionHomeFragmentToWallpaperFragment(url), extras)
            },
            onCollectionClick = { data ->
                findNavController().navigate(
                    actionHomeFragmentToPhotoCollectionFragment(
                        data.id, data.title, data.photosCount
                    )
                )
            })
        val footerAdapter = GeneralLoadStateAdapter { homeAdapter.retry() }
        bindingView.rvHome.apply {
            this.adapter = homeAdapter.withLoadStateFooter(footerAdapter)
            this.layoutManager = getGridLayoutManager(footerAdapter)
            this.addItemDecoration(HomeDecorator(resources))
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { uiState ->
                    homeAdapter.submitData(lifecycle, uiState.homeItemViews)
                }
            }
        }
    }

    private fun getGridLayoutManager(footerAdapter: GeneralLoadStateAdapter): GridLayoutManager {
        return GridLayoutManager(context, 2).apply {
            spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val hasFooter = footerAdapter.itemCount > 0 && position == homeAdapter.itemCount
                    val isCollection = position < homeAdapter.itemCount &&
                        homeAdapter.getItemViewType(position) == R.layout.item_collection
                    if (hasFooter || !isCollection) {
                        return spanCount
                    }
                    return 1
                }
            }
        }
    }
}
