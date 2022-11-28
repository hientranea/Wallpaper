package com.hientran.wallpaper.data.remote.datasource

import androidx.paging.PagingSource
import com.hientran.wallpaper.data.WallpaperRemoteDataSource
import com.hientran.wallpaper.data.local.WallpaperDatabase
import com.hientran.wallpaper.data.local.entities.SearchEntity
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.datasource.pagingsource.CollectionMediaPagingSource
import com.hientran.wallpaper.data.remote.datasource.pagingsource.HomePagingSource
import com.hientran.wallpaper.data.remote.datasource.pagingsource.WallpaperPagingSource
import com.hientran.wallpaper.data.remote.services.PexelsApiService

class WallpaperRemoteDataSourceImpl(
    private val database: WallpaperDatabase,
    private val apiService: PexelsApiService,
): WallpaperRemoteDataSource {
    override fun getPhotoByQueryMediator(searchEntity: SearchEntity): PhotoByQueryRemoteMediator {
        return PhotoByQueryRemoteMediator(database, apiService, searchEntity)
    }

    override fun getPhotoByCollectionMediator(collectionId: String): PhotoByCollectionRemoteMediator {
        return PhotoByCollectionRemoteMediator(database, apiService, collectionId)
    }

    override fun getHomePagingSource(): HomePagingSource {
        return HomePagingSource(apiService)
    }

    override fun getPhotoByCollectionPagingSource(collectionId: String): PagingSource<Int, WallpaperEntity> {
        return CollectionMediaPagingSource(apiService, collectionId)
    }

    override fun getPhotoByQueryPagingSource(query: String): PagingSource<Int, WallpaperEntity> {
        return WallpaperPagingSource(apiService, query)
    }
}
