package com.hientran.wallpaper.presentation.ui.common.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hientran.wallpaper.databinding.ItemLoadStateBinding

data class GeneralLoadStateTheme(val backgroundColor: Int = Color.WHITE)

class GeneralLoadStateAdapter(
    private val theme: GeneralLoadStateTheme = GeneralLoadStateTheme(),
    private val retry: () -> Unit
): LoadStateAdapter<GeneralLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoadStateBinding.inflate(inflater, parent, false)
        return LoadStateViewHolder(binding, retry, theme)
    }

    class LoadStateViewHolder(
        private val bindingView: ItemLoadStateBinding,
        private val onRetry: () -> Unit,
        private val theme: GeneralLoadStateTheme
    ): RecyclerView.ViewHolder(bindingView.root) {

        init {
            bindingView.btLoadStateRetry.setOnClickListener { onRetry.invoke() }
        }

        fun bind(item: LoadState) {
            bindingView.apply {
                clLoadState.setBackgroundColor(theme.backgroundColor)
                pbLoadState.isVisible = item is LoadState.Loading
                groupLoadStateError.isVisible = item !is LoadState.Loading
                if (item is LoadState.Error && !item.error.message.isNullOrEmpty()) {
                    tvLoadStateError.text = item.error.message
                }
            }
        }
    }
}
