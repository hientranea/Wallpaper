package com.hientran.wallpaper.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hientran.wallpaper.data.exception.AppException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel: ViewModel() {
    private val _baseState = MutableStateFlow(BaseViewState())
    val baseState: StateFlow<BaseViewState> = _baseState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
        hideLoading()
        if (exception is AppException) showError(exception)
    }

    open fun showError(error: AppException) {
        _baseState.update { it.copy(error = error) }
    }

    open fun hideLoading() {
        _baseState.update { it.copy(isLoading = false) }
    }

    open fun showLoading() {
        _baseState.update { it.copy(isLoading = true) }
    }

    fun callApi(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ): Job? {
        var job: Job? = null
        viewModelScope.launch(context + coroutineExceptionHandler, start) {
            job = launch { block.invoke(this) }
            job?.join()
        }
        return job
    }
}

object BlankViewModel : BaseViewModel()
