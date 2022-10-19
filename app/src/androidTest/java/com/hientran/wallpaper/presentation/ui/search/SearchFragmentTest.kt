package com.hientran.wallpaper.presentation.ui.search

import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.hientran.wallpaper.R
import com.hientran.wallpaper.util.launchFragmentWithNav
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import launchFragmentInHiltContainer
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SearchFragmentTest {
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)
    private val bundle = SearchFragmentArgs("dogs").toBundle()
    private val navController = TestNavHostController(getApplicationContext())

    @Test
    fun renderSearchListCorrectly() {
        launchFragmentInHiltContainer<SearchFragment>(bundle)
        assertDisplayed(R.id.tv_search_title, "Dogs")
        assertListItemCount(R.id.rv_search, 5)
    }

    @Test
    fun clickBackButton_navigateBack() {
        // GIVEN: On photo collection screen
        launchFragmentWithNav<SearchFragment>(navController, R.id.searchFragment, bundle)

        // WHEN: Click on back button
        onView(
            allOf(
                instanceOf(AppCompatImageButton::class.java),
                withParent(withId(R.id.tb_search))
            )
        ).perform(click())

        // THEN: Verity that navigated back to home screen
        assertEquals(navController.currentDestination?.id, R.id.homeFragment)
    }

    @Test
    fun clickOnPhoto_openWallpaperScreen() {
        // GIVEN: On photo collection screen
        launchFragmentWithNav<SearchFragment>(navController, R.id.searchFragment, bundle)

        // WHEN: click on photo item in list view
        clickListItem(R.id.rv_search, 0)

        // THEN: open wallpaper screen
        assertEquals(navController.currentDestination?.id, R.id.wallpaperFragment)
    }
}
