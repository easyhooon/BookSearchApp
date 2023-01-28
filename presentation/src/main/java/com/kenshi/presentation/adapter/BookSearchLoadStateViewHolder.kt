package com.kenshi.presentation.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kenshi.presentation.R
import com.kenshi.presentation.databinding.ItemLoadStateBinding

// loagingState 를 관리해주는 adapter 의 뷰홀더
class BookSearchLoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    retry: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) = with(binding) {
        if (loadState is LoadState.Error) {
            tvError.text = itemView.context.getString(R.string.error_occurred)
        }
        progressBar.isVisible = loadState is LoadState.Loading
        btnRetry.isVisible = loadState is LoadState.Error
        tvError.isVisible = loadState is LoadState.Error
    }
}