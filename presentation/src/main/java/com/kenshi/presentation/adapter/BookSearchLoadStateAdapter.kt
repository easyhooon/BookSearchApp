package com.kenshi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.kenshi.presentation.databinding.ItemLoadStateBinding

class BookSearchLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<BookSearchLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): BookSearchLoadStateViewHolder {
        return BookSearchLoadStateViewHolder(
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )
    }

    override fun onBindViewHolder(holder: BookSearchLoadStateViewHolder, loadState: LoadState) {
        // binding 시 loadState 를 전달
        holder.bind(loadState)
    }
}