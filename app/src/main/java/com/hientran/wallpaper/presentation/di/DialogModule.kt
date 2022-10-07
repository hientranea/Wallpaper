package com.hientran.wallpaper.presentation.di

import com.hientran.wallpaper.presentation.ui.common.DialogUtil
import com.hientran.wallpaper.presentation.ui.common.DialogUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DialogModule {
    @Provides
    fun providesDialogUtil(): DialogUtil = DialogUtilImpl()
}
