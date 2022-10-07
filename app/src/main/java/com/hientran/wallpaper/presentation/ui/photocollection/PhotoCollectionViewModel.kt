package com.hientran.wallpaper.presentation.ui.photocollection

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hientran.wallpaper.domain.usecase.GetCollectionMediaUseCase
import com.hientran.wallpaper.domain.usecase.GetCollectionMediaUseCase.Params
import com.hientran.wallpaper.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PhotoCollectionViewModel @Inject constructor(
    private val getCollectionMediaUseCase: GetCollectionMediaUseCase
): BaseViewModel() {
    private val _state = MutableStateFlow(PhotoCollectionViewState())
    val state: StateFlow<PhotoCollectionViewState> = _state

    fun getCollectionMedia(id: String) {
        callApi {
            getCollectionMediaUseCase(Params(id))
                .cachedIn(viewModelScope)
                .collectLatest { photos ->
                    _state.update { it.copy(photos = photos) }
                }
        }
    }
}
