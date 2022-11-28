package com.hientran.wallpaper.presentation.base

import com.hientran.wallpaper.data.AppException

data class BaseViewState(
    val isLoading: Boolean = false,
    val error: AppException? = null
)
