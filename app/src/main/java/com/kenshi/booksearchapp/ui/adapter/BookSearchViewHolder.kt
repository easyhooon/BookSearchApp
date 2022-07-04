package com.kenshi.booksearchapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kenshi.booksearchapp.data.model.SearchResponse
import com.kenshi.booksearchapp.databinding.ItemBookPreviewBinding

class BookSearchViewHolder(
    private val binding: ItemBookPreviewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(book: SearchResponse.Book) {
        // 좌우 꺽쇄 날림
        val author = book.authors.toString().removeSurrounding("[", "]")
        val publisher = book.publisher
        // nullable 하기 때문에 방어코드 작성
        val date = if (book.datetime.isNotEmpty()) book.datetime.substring(0, 10) else ""

        itemView.apply {
            binding.apply {
                ivArticleImage.load(book.thumbnail)
                tvTitle.text = book.title
                tvAuthor.text = "$author | $publisher"
                tvDatetime.text = date
            }
        }

    }
}