package com.hientran.wallpaper.presentation.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hientran.wallpaper.domain.usecase.GetHomeDataUseCase
import com.hientran.wallpaper.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase
): BaseViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = _state

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        callApi {
            getHomeDataUseCase(GetHomeDataUseCase.Params).cachedIn(viewModelScope)
                .collectLatest { views ->
                    _state.update {
                        it.copy(homeItemViews = views)
                    }
                }
        }
    }
}


