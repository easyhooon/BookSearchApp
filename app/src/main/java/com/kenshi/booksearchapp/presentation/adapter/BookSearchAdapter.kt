package com.kenshi.booksearchapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kenshi.booksearchapp.databinding.ItemBookPreviewBinding
import com.kenshi.booksearchapp.presentation.item.BookItem

class BookSearchAdapter : ListAdapter<BookItem, BookSearchViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        return BookSearchViewHolder(
            ItemBookPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        val bookItem = currentList[position]
        holder.bind(bookItem)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(bookItem) }
        }
    }

    private var onItemClickListener: ((BookItem) -> Unit)? = null
    fun setOnItemClickListener(listener: (BookItem) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val BookDiffCallback = object : DiffUtil.ItemCallback<BookItem>() {
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