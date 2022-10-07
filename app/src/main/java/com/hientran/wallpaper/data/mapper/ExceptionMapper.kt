package com.hientran.wallpaper.data.mapper

import com.hientran.wallpaper.R
import com.hientran.wallpaper.data.exception.AppException
import retrofit2.HttpException
import java.io.IOException

fun Throwable.mapperToAppException(): Throwable =
    when (this) {
        is IOException -> AppException(code = -1, messageId = R.string.error_message_network)
        is HttpException -> AppException(
            code = this.code(), message = this.message ?: ""
        )
        else -> this
    }
