package com.hientran.wallpaper.base

import androidx.room.RoomDatabase

interface MockDatabaseTransaction {
    fun getDatabase() : RoomDatabase
}
