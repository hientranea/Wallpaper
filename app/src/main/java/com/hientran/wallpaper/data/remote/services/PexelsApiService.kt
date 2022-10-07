package com.hientran.wallpaper.data.remote.services

import com.hientran.wallpaper.common.DEFAULT_PER_PAGE
import com.hientran.wallpaper.data.model.CollectionList
import com.hientran.wallpaper.data.model.CollectionMedia
import com.hientran.wallpaper.data.model.WallpaperList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApiService {
    @GET("search")
    suspend fun search(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = DEFAULT_PER_PAGE,
        @Query("page") page: Int = 1,
    ): WallpaperList

    @GET("curated")
    suspend fun getCuratedPhotos(): WallpaperList

    @GET("collections/featured")
    suspend fun featuredCollections(
        @Query("per_page") perPage: Int = DEFAULT_PER_PAGE,
        @Query("page") page: Int = 1,
    ): CollectionList

    @GET("collections/{id}")
    suspend fun getCollectionMedia(
        @Path("id") id: String,
        @Query("per_page") perPage: Int = DEFAULT_PER_PAGE,
        @Query("page") page: Int = 1,
        @Query("type") type: String = "photos"
    ): CollectionMedia
}
