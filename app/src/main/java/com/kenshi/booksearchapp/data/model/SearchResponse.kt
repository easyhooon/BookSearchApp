package com.kenshi.booksearchapp.data.model


import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class SearchResponse(
    val meta: Meta,
    val documents: List<Book>,
)