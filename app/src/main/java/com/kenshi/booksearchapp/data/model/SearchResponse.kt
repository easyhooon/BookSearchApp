package com.kenshi.booksearchapp.data.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class SearchResponse(
    @field:Json(name = "meta")
    val meta: Meta,
    @field:Json(name = "documents")
    val documents: List<Book>,
) {
    //Json 만으로는 kotlin 에서 변환 실패함 field 추가
    @Keep
    @JsonClass(generateAdapter = true)
    data class Meta(
        @field:Json(name = "is_end")
        val isEnd: Boolean,
        @field:Json(name = "pageable_count")
        val pageableCount: Int,
        @field:Json(name = "total_count")
        val totalCount: Int,
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Book(
        @field:Json(name = "authors")
        val authors: List<String>,
        @field:Json(name = "contents")
        val contents: String,
        @field:Json(name = "datetime")
        val datetime: String,
        @field:Json(name = "isbn")
        val isbn: String,
        @field:Json(name = "price")
        val price: Int,
        @field:Json(name = "publisher")
        val publisher: String,
        @field:Json(name = "sale_price")
        val salePrice: Int,
        @field:Json(name = "status")
        val status: String,
        @field:Json(name = "thumbnail")
        val thumbnail: String,
        @field:Json(name = "title")
        val title: String,
        @field:Json(name = "translators")
        val translators: List<String>,
        @field:Json(name = "url")
        val url: String,
    )
}