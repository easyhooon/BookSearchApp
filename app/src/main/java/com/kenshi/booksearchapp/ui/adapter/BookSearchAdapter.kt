package com.kenshi.booksearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kenshi.booksearchapp.data.model.SearchResponse
import com.kenshi.booksearchapp.databinding.ItemBookPreviewBinding

class BookSearchAdapter : ListAdapter<SearchResponse.Book, BookSearchViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        return BookSearchViewHolder(
            ItemBookPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        val book = currentList[position]
        holder.bind(book)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(book) }
        }
    }

    private var onItemClickListener: ((SearchResponse.Book) -> Unit)? = null
    fun setOnItemClickListener(listener: (SearchResponse.Book) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val BookDiffCallback = object : DiffUtil.ItemCallback<SearchResponse.Book>() {
            override fun areItemsTheSame(
                oldItem: SearchResponse.Book,
                newItem: SearchResponse.Book,
            ): Boolean {
                return oldItem.isbn == newItem.isbn
            }

            override fun areContentsTheSame(
                oldItem: SearchResponse.Book,
                newItem: SearchResponse.Book,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}