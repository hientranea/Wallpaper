package com.hientran.wallpaper.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hientran.wallpaper.presentation.ui.common.DialogUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes layoutId: Int): Fragment(layoutId) {
    abstract val viewModel: BaseViewModel

    @Inject lateinit var dialogUtil: DialogUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.baseState.collect { uiState ->
                    if (uiState.isLoading) {
                        dialogUtil.showProgressDialog(view)
                    } else {
                        dialogUtil.hideProgressDialog()
                    }
                }
            }
        }
    }

    open fun observeState(block: suspend CoroutineScope.() -> Unit)  {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                block()
            }
        }
    }
}
