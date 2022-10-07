package com.hientran.wallpaper.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.hientran.wallpaper.R
import launchFragmentInHiltContainer

inline fun <reified T: Fragment> launchFragmentWithNav(
    navController: TestNavHostController,
    curDesId: Int,
    bundle: Bundle? = null
) {
    launchFragmentInHiltContainer<T>(bundle) {
        navController.setGraph(R.navigation.nav_graph)
        bundle?.let {
            navController.setCurrentDestination(curDesId, bundle)
        }
        Navigation.setViewNavController(requireView(), navController)
    }
}
