package com.hientran.wallpaper.presentation.ui.home

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.hientran.wallpaper.BaseAndroidTest
import com.hientran.wallpaper.R
import com.hientran.wallpaper.util.launchFragmentWithNav
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import launchFragmentInHiltContainer
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeFragmentTest: BaseAndroidTest() {
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)
    private val navController = TestNavHostController(getApplicationContext())

    @Test
    fun renderHomeListDataCorrectly() {
        launchFragmentInHiltContainer<HomeFragment>()
        assertListItemCount(R.id.rv_home, 7)
        assertDisplayedAtPosition(R.id.rv_home, 1, R.string.home_best_of_month)
        assertListItemCount(R.id.rv_home_curated_featured, 1)
        assertDisplayedAtPosition(R.id.rv_home, 3, R.string.home_categories)
        assertDisplayedAtPosition(R.id.rv_home, 4, R.id.tv_item_collection, "A")
        assertDisplayedAtPosition(R.id.rv_home, 5, R.id.tv_item_collection, "B")
        assertDisplayedAtPosition(R.id.rv_home, 6, R.id.tv_item_collection, "C")
    }

    @Test
    fun clickOnPhoto_openWallpaperScreen() {
        // GIVEN: On home screen
        launchFragmentWithNav<HomeFragment>(navController, R.id.homeFragment)
        // WHEN: click on photo item in list view
        clickListItem(R.id.rv_home_curated_featured, 0)
        // THEN: open wallpaper screen
        assertEquals(navController.currentDestination?.id, R.id.wallpaperFragment)
    }

    @Test
    fun clickOnCollection_openCollectionScreen() {
        // GIVEN: On home screen
        launchFragmentWithNav<HomeFragment>(navController, R.id.homeFragment)
        // WHEN: click on collection item in list view
        clickListItem(R.id.rv_home, 4)
        // THEN: open collection screen
        assertEquals(navController.currentDestination?.id, R.id.photoCollectionFragment)
    }


}
