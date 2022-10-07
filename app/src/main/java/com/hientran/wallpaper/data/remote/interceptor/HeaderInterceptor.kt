package com.hientran.wallpaper.data.remote.interceptor

import com.hientran.wallpaper.BuildConfig
import com.hientran.wallpaper.common.HEADER_AUTHORIZATION
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = request.newBuilder()
            .addHeader(HEADER_AUTHORIZATION, BuildConfig.PEXEL_API_KEY)
            .build()

        return chain.proceed(request)
    }
}

