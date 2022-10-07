package com.hientran.wallpaper.data.mapper

import com.hientran.wallpaper.R
import com.hientran.wallpaper.data.exception.AppException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class ExceptionMapperTest {
    private lateinit var throwable: Throwable

    @Test
    fun `throwable is IO exception`() {
        throwable = IOException()
        assertEquals(
            AppException(code = -1, messageId = R.string.error_message_network),
            throwable.mapperToAppException()
        )
    }

    @Test
    fun `throwable is Http exception`() {
        throwable = HttpException(
            Response.error<Any>(
                401, ResponseBody.create("plain/text".toMediaTypeOrNull(), "")
            )
        )
        assertEquals(
            AppException(code = 401, message = "HTTP 401 Response.error()"),
            throwable.mapperToAppException()
        )
    }
}
