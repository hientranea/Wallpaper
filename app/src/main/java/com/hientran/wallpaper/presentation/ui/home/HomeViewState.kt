package com.hientran.wallpaper.presentation.ui.home

import androidx.paging.PagingData

data class HomeViewState(
    val homeItemViews: PagingData<HomeItemView> = PagingData.empty(),
)
