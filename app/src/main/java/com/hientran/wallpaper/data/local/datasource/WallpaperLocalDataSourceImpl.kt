package com.hientran.wallpaper.data.local.datasource

import androidx.paging.PagingSource
import com.hientran.wallpaper.data.WallpaperLocalDataSource
import com.hientran.wallpaper.data.local.dao.SearchDao
import com.hientran.wallpaper.data.local.dao.WallpaperDao
import com.hientran.wallpaper.data.local.entities.SearchEntity
import com.hientran.wallpaper.data.local.entities.WallpaperEntity

class WallpaperLocalDataSourceImpl(
    private val searchDao: SearchDao,
    private val wallpaperDao: WallpaperDao
): WallpaperLocalDataSource {
    override suspend fun saveSearchQuery(query: String): SearchEntity {
        return searchDao.getOrCreate(query = query)
    }

    override fun getPhotoByQueryPagingSource(query: String): PagingSource<Int, WallpaperEntity> {
        return wallpaperDao.pagingSourceBySearchQuery(query)
    }

    override fun getPhotoByCollectionPagingSource(collectionId: String): PagingSource<Int, WallpaperEntity> {
        return wallpaperDao.pagingSourceByCollection(collectionId)
    }
}
