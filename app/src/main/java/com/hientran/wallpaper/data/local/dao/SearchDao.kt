package com.hientran.wallpaper.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hientran.wallpaper.data.local.entities.SearchEntity

@Dao
interface SearchDao {
    @Query("DELETE FROM search_results")
    suspend fun clearAll()

    @Query("DELETE FROM search_results WHERE `query` = :query")
    suspend fun clearAllByQuery(query: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(query: SearchEntity): Long

    @Query("SELECT * FROM search_results WHERE `query` = :query")
    suspend fun findByQuery(query: String): List<SearchEntity>

    @Transaction
    suspend fun getOrCreate(query: String): SearchEntity {
        val records = findByQuery(query)

        return if (records.isNotEmpty()) {
            SearchEntity(id = records.first().id, query = query)
        } else {
            val newRecord = SearchEntity(query = query)
            insert(newRecord).apply { newRecord.id = this }
            newRecord
        }
    }
}
