package com.hientran.wallpaper.data.exception

import androidx.annotation.StringRes

data class AppException(
    val code: Int,
    override val message: String = "",
    @StringRes val messageId: Int? = null
): Throwable()
