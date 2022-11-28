@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)

package com.hientran.wallpaper.data.remote.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.InitializeAction
import androidx.room.RoomDatabase
import com.hientran.wallpaper.base.BaseTest
import com.hientran.wallpaper.base.MockDatabaseTransaction
import com.hientran.wallpaper.data.local.WallpaperDatabase
import com.hientran.wallpaper.data.local.dao.RemoteKeyDao
import com.hientran.wallpaper.data.local.dao.WallpaperDao
import com.hientran.wallpaper.data.local.entities.RemoteKeyEntity
import com.hientran.wallpaper.data.local.entities.WallpaperEntity
import com.hientran.wallpaper.data.remote.datasource.PhotoByCollectionRemoteMediator.Companion.PHOTO_BY_COLLECTION_PREFIX
import com.hientran.wallpaper.data.remote.model.CollectionMedia
import com.hientran.wallpaper.data.remote.model.WallpaperList
import com.hientran.wallpaper.data.remote.model.WallpaperPhoto
import com.hientran.wallpaper.data.remote.model.toWallpaperEntity
import com.hientran.wallpaper.data.remote.services.PexelsApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PhotoByCollectionRemoteMediatorTest: BaseTest(), MockDatabaseTransaction {
    private val collectionId = "collectionId"
    private val remoteKeyLabel = PHOTO_BY_COLLECTION_PREFIX + collectionId
    private val mockPhotos = listOf(WallpaperPhoto())
    private val mockResponse = WallpaperList(photos = mockPhotos)
    private val mockPhotoDao = mockk<WallpaperDao>(relaxed = true)
    private val mockRemoteKeyDao = mockk<RemoteKeyDao>(relaxed = true)
    private val database = mockk<WallpaperDatabase>(relaxed = true) {
        every { wallpaperDao() } returns mockPhotoDao
        every { remoteKeyDao() } returns mockRemoteKeyDao
    }
    private val service = mockk<PexelsApiService>(relaxed = true) {
        coEvery { search(any(), any(), any()) } returns mockResponse
    }
    private val mediator = PhotoByCollectionRemoteMediator(database, service, collectionId)

    override fun getDatabase(): RoomDatabase = database

    @Test
    fun `initializes with with refresh type`() {
        runTest {
            assertEquals(InitializeAction.SKIP_INITIAL_REFRESH, mediator.initialize())
        }
    }

    @Test
    fun `refresh load returns success result when more data is present`() {
        val state = PagingState<Int, WallpaperEntity>(listOf(), null, PagingConfig(10), 10)
        val listPhotos = listOf(WallpaperPhoto())
        coEvery { service.getCollectionMedia(id = collectionId, page = 1) } returns CollectionMedia(
            photos = listPhotos
        )

        runTest {
            val result = mediator.load(REFRESH, state)
            coVerify { mockPhotoDao.clearAllByCollection(collectionId) }
            coVerify { mockRemoteKeyDao.clearKeyWithLabel(remoteKeyLabel) }
            coVerify {
                mockRemoteKeyDao.saveNewKey(
                    RemoteKeyEntity(label = remoteKeyLabel, nextPage = 2, nLastItems = 1)
                )
            }
            coVerify { mockPhotoDao.insertAll(listPhotos.map { it.toWallpaperEntity(collectionId = collectionId) }) }
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }
    }

    @Test
    fun `refresh load returns success result when no more data`() {
        val state = PagingState<Int, WallpaperEntity>(listOf(), null, PagingConfig(10), 10)
        coEvery { service.getCollectionMedia(id = collectionId, page = 1) } returns CollectionMedia(
            photos = emptyList()
        )

        runTest {
            val result = mediator.load(REFRESH, state)
            coVerify {
                mockRemoteKeyDao.saveNewKey(
                    RemoteKeyEntity(label = remoteKeyLabel, nextPage = 2, nLastItems = 0)
                )
            }
            coVerify { mockPhotoDao.insertAll(emptyList()) }
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }
    }

    @Test
    fun `prepend load returns success with end of pagination has reached`() {
        val state = PagingState<Int, WallpaperEntity>(listOf(), null, PagingConfig(10), 10)
        runTest {
            val result = mediator.load(PREPEND, state)
            coVerify(exactly = 0) { service.getCollectionMedia(any(), any(), any(), any()) }
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }
    }

    @Test
    fun `append load returns success with end of pagination has reached when the last item is loaded`() {
        val state = PagingState<Int, WallpaperEntity>(listOf(), null, PagingConfig(10), 10)
        coEvery { mockRemoteKeyDao.getKeys(remoteKeyLabel) } returns emptyList()
        runTest {
            val result = mediator.load(APPEND, state)
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }
    }

    @Test
    fun `append load returns success when more data is present`() {
        val state = PagingState<Int, WallpaperEntity>(
            listOf(PagingSource.LoadResult.Page(listOf(), null, null)), null, PagingConfig(10), 10
        )
        val listPhotos = listOf(WallpaperPhoto())
        coEvery { service.getCollectionMedia(id = collectionId, page = 1) } returns CollectionMedia(
            photos = listPhotos
        )
        coEvery { mockRemoteKeyDao.getKeys(remoteKeyLabel) } returns listOf(
            RemoteKeyEntity(remoteKeyLabel, 1, 10)
        )

        runTest {
            val result = mediator.load(APPEND, state)
            coVerify(exactly = 0) { mockPhotoDao.clearAllByCollection(any()) }
            coVerify(exactly = 0) { mockRemoteKeyDao.clearKeyWithLabel(any()) }
            coVerify {
                mockRemoteKeyDao.saveNewKey(
                    RemoteKeyEntity(label = remoteKeyLabel, nextPage = 2, nLastItems = 1)
                )
            }
            coVerify { mockPhotoDao.insertAll(listPhotos.map { it.toWallpaperEntity(collectionId = collectionId) }) }
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }
    }
}