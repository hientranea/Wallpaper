package com.hientran.wallpaper.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {
    @Query("SELECT * FROM wallpapers")
    suspend fun getWallpaperPhotos(): List<WallpaperEntity>

    @Query("DELETE FROM wallpapers")
    suspend fun clearAll()

    @Query("DELETE FROM wallpapers WHERE collectionId = :collectionId")
    suspend fun clearAllByCollection(collectionId: String)

    @Query("DELETE FROM wallpapers WHERE searchId = :searchId")
    suspend fun clearAllBySearchQuery(searchId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<WallpaperEntity>)

    @Query("SELECT * FROM wallpapers WHERE collectionId LIKE :id")
    fun pagingSourceByCollection(id: String): PagingSource<Int, WallpaperEntity>

    @Query("SELECT wallpapers.* FROM wallpapers JOIN search_results ON search_results.id = wallpapers.searchId WHERE  search_results.`query` = :query")
    fun pagingSourceBySearchQuery(query: String): PagingSource<Int, WallpaperEntity>
}
