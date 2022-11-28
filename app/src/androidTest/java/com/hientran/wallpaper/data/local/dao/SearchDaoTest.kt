package com.hientran.wallpaper.data.local.dao

import com.hientran.wallpaper.BaseDatabaseTest
import com.hientran.wallpaper.data.local.entities.SearchEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SearchDaoTest: BaseDatabaseTest() {
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    override fun getHiltRule() = hiltAndroidRule

    @Test
    fun insertSearchQuery(): Unit = runBlocking {
        val dao = database.searchDao()
        dao.insert(SearchEntity(query = "happy"))

        val response: SearchEntity? = dao.findByQuery("happy").firstOrNull()
        assertEquals("happy", response!!.query)
    }

    @Test
    fun clearAllByQuery(): Unit = runBlocking {
        val dao = database.searchDao()
        dao.apply {
            insert(SearchEntity(query = "happy"))
            clearAllByQuery("happy")
        }
        val response: SearchEntity? = dao.findByQuery("happy").firstOrNull()
        assertEquals(null, response)
    }

    @Test
    fun returnsExistingEntityIfCreateWithExistingQuery(): Unit = runBlocking {
        val dao = database.searchDao()
        dao.apply {
            val id = insert(SearchEntity(query = "happy"))
            val entity = getOrCreate("happy")
            assertEquals(id, entity.id)
        }
    }

    @Test
    fun returnsNewEntityIfCreateWithExistingQuery(): Unit = runBlocking {
        val dao = database.searchDao()
        var response = dao.findByQuery("happy")
        assertEquals(emptyList<SearchEntity>(), response)

        val entity = dao.getOrCreate("happy")
        response = dao.findByQuery("happy")
        assertEquals(response.first().id, entity.id)
    }
}
