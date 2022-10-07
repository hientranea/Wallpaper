package com.hientran.wallpaper.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in Params, out T> constructor(private val coroutineDispatcher: CoroutineDispatcher) {
    protected abstract fun execute(params: Params): Flow<T>

    operator fun invoke(params: Params): Flow<T> = execute(params).flowOn(coroutineDispatcher)
}
