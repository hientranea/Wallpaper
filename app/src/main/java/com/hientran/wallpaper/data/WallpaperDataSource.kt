package com.hientran.wallpaper.data

import androidx.paging.PagingSource
import com.hientran.wallpaper.data.local.entities.SearchEntity
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.datasource.PhotoByCollectionRemoteMediator
import com.hientran.wallpaper.data.remote.datasource.PhotoByQueryRemoteMediator
import com.hientran.wallpaper.data.remote.datasource.pagingsource.HomePagingSource

interface WallpaperDataSource {
    fun getPhotoByCollectionPagingSource(collectionId: String): PagingSource<Int, WallpaperEntity>

    fun getPhotoByQueryPagingSource(query: String): PagingSource<Int, WallpaperEntity>
}

interface WallpaperLocalDataSource: WallpaperDataSource {
    suspend fun saveSearchQuery(query: String): SearchEntity
}

interface WallpaperRemoteDataSource: WallpaperDataSource {
    fun getPhotoByQueryMediator(searchEntity: SearchEntity): PhotoByQueryRemoteMediator

    fun getPhotoByCollectionMediator(collectionId: String): PhotoByCollectionRemoteMediator

    fun getHomePagingSource(): HomePagingSource
}

