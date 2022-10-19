package com.hientran.wallpaper.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.hientran.wallpaper.R
import com.hientran.wallpaper.common.WALLPAPER_TRANS_NAME
import com.hientran.wallpaper.common.extensions.makeCapitalize
import com.hientran.wallpaper.databinding.FragmentSearchBinding
import com.hientran.wallpaper.presentation.base.BaseFragment
import com.hientran.wallpaper.presentation.ui.common.adapter.GeneralLoadStateAdapter
import com.hientran.wallpaper.presentation.ui.common.adapter.WallpaperPagingAdapter
import com.hientran.wallpaper.presentation.ui.common.viewBinding
import com.hientran.wallpaper.presentation.ui.photocollection.PhotoCollectionDecorator
import com.hientran.wallpaper.presentation.ui.search.SearchFragmentDirections.Companion.actionSearchFragmentToWallpaperFragment

class SearchFragment: BaseFragment(R.layout.fragment_search) {
    override val viewModel: SearchViewModel by viewModels()
    private val bindingView by viewBinding(FragmentSearchBinding::bind)
    private lateinit var pagingAdapter: WallpaperPagingAdapter
    private val args: SearchFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeUiState()
        viewModel.search(args.query)
    }

    private fun initViews() {
        pagingAdapter = WallpaperPagingAdapter { photoView, url ->
            ViewCompat.setTransitionName(photoView, WALLPAPER_TRANS_NAME)
            val extras = FragmentNavigatorExtras(photoView to WALLPAPER_TRANS_NAME)
            findNavController().navigate(actionSearchFragmentToWallpaperFragment(url), extras)
        }
        val footerAdapter = GeneralLoadStateAdapter { pagingAdapter.retry() }
        with(bindingView) {
            tvSearchTitle.text = args.query.makeCapitalize()
            rvSearch.apply {
                adapter = pagingAdapter.withLoadStateFooter(footerAdapter)
                layoutManager = GridLayoutManager(context, 2)
                addItemDecoration(PhotoCollectionDecorator(resources, 2))
            }
            tbSearch.navigationIcon = getDrawable(resources, R.drawable.ic_black_back, null)
            tbSearch.setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun observeUiState() {
        observeState {
            viewModel.state.collect {
                pagingAdapter.submitData(lifecycle, it.photos)
            }
        }
    }
}
