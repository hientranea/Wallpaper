package com.hientran.wallpaper.presentation.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hientran.wallpaper.domain.usecase.SearchPhotoUseCase
import com.hientran.wallpaper.presentation.base.BaseViewModel
import com.hientran.wallpaper.presentation.ui.photocollection.PhotoCollectionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPhotoUseCase: SearchPhotoUseCase
): BaseViewModel() {
    private val _state = MutableStateFlow(SearchViewState())
    val state: StateFlow<SearchViewState> = _state

    fun search(query: String) {
        callApi {
            searchPhotoUseCase(SearchPhotoUseCase.Params(query))
                .cachedIn(viewModelScope)
                .collectLatest { photos ->
                    _state.update { it.copy(photos = photos) }
                }
        }
    }
}
