package com.hientran.wallpaper.data.local.dao

import com.hientran.wallpaper.BaseDatabaseTest
import com.hientran.wallpaper.data.local.entities.RemoteKeyEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class RemoteKeyDaoTest: BaseDatabaseTest() {
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    override fun getHiltRule() = hiltAndroidRule

    @Test
    fun saveAndClearKey(): Unit = runBlocking {
        val dao = database.remoteKeyDao()
        val label = "label"

        // Save new key
        dao.saveNewKey(RemoteKeyEntity(label = label, nextPage = 1))
        var keys = dao.getKeys(label)
        assertEquals(1, keys.first().nextPage)

        // Update current key
        dao.saveNewKey(RemoteKeyEntity(label = label, nextPage = 10))
        keys = dao.getKeys(label)
        assertEquals(10, keys.first().nextPage)

        // Clear key
        dao.clearKeyWithLabel(label)
        keys = dao.getKeys(label)
        assertEquals(0, keys.size)
    }
}
