package com.kenshi.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kenshi.presentation.databinding.ItemBookPreviewBinding
import com.kenshi.presentation.item.BookItem

class BookSearchViewHolder(
    private val binding: ItemBookPreviewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(bookItem: BookItem) {
        // 좌우 꺽쇄 날림
        val author = bookItem.authors.toString().removeSurrounding("[", "]")
        val publisher = bookItem.publisher
        // nullable 하기 때문에 방어코드 작성
        val date = if (bookItem.datetime.isNotEmpty()) bookItem.datetime.substring(0, 10) else ""

        itemView.apply {
            binding.apply {
                ivArticleImage.load(bookItem.thumbnail)
                tvTitle.text = bookItem.title
                tvAuthor.text = "$author | $publisher"
                tvDatetime.text = date
            }
        }
    }
}
