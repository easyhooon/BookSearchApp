package com.kenshi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kenshi.presentation.databinding.ItemBookPreviewBinding
import com.kenshi.presentation.item.BookItem

class BookSearchPagingAdapter : PagingDataAdapter<BookItem, BookSearchViewHolder>(
    BookItemDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        return BookSearchViewHolder(
            ItemBookPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        // nullable
        val pagedBook = getItem(position)
        // null 대응
        pagedBook?.let { book ->
            holder.bind(book)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(book) }
            }
        }
    }

    private var onItemClickListener: ((BookItem) -> Unit)? = null
    fun setOnItemClickListener(listener: (BookItem) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val BookItemDiffCallback = object : DiffUtil.ItemCallback<BookItem>() {
            override fun areItemsTheSame(
                oldItem: BookItem,
                newItem: BookItem,
            ): Boolean {
                return oldItem.isbn == newItem.isbn
            }

            override fun areContentsTheSame(
                oldItem: BookItem,
                newItem: BookItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
