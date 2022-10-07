package com.hientran.wallpaper.data.di

import com.hientran.wallpaper.BuildConfig
import com.hientran.wallpaper.data.remote.calladapter.FlowCallAdapterFactory
import com.hientran.wallpaper.data.remote.interceptor.HeaderInterceptor
import com.hientran.wallpaper.data.remote.services.PexelsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideGeneralOkHttpBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .callTimeout(TIME_OUT, TimeUnit.MINUTES)
            .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder
    }

    @Provides
    fun provideOkHttpClient(
        okHttpBuilder: OkHttpClient.Builder,
        headerInterceptor: HeaderInterceptor,
    ): OkHttpClient = okHttpBuilder.addInterceptor(headerInterceptor).build()

    @Provides
    fun provideRetrofit(
        flowCallAdapterFactory: FlowCallAdapterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(flowCallAdapterFactory)
        .build()

    @Provides
    fun providePexelsApiService(retrofit: Retrofit): PexelsApiService =
        retrofit.create(PexelsApiService::class.java)
}

private const val TIME_OUT = 1L
