package com.hientran.wallpaper.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hientran.wallpaper.data.local.entities.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Query("DELETE FROM remote_keys WHERE label = :label")
    suspend fun clearKeyWithLabel(label: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewKey(key: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys WHERE label LIKE :label")
    fun getKeys(label: String): List<RemoteKeyEntity>
}
