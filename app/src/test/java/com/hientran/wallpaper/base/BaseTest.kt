package com.hientran.wallpaper.base

import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
open class BaseTest {
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    open fun setup() {
        Dispatchers.setMain(dispatcher)
        if (this is MockPaging) {
            mockkStatic("androidx.paging.CachedPagingDataKt")
        }
    }

    @After
    open fun teardown() {
        if (this is MockPaging) {
            unmockkStatic("androidx.paging.CachedPagingDataKt")
        }
        Dispatchers.resetMain()
    }
}
