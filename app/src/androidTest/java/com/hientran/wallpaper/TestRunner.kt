package com.hientran.wallpaper

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.hientran.wallpaper.presentation.WallPaperApp
import dagger.hilt.android.testing.HiltTestApplication

class TestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
