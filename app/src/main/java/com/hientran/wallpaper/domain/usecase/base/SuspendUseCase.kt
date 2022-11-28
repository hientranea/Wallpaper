package com.hientran.wallpaper.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SuspendUseCase<in Params, out T> constructor(private val coroutineDispatcher: CoroutineDispatcher) {
    protected abstract suspend fun execute(params: Params): T

    suspend operator fun invoke(params: Params): T = withContext(coroutineDispatcher) {
        execute(params)
    }
}
