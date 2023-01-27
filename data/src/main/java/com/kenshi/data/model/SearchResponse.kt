package com.kenshi.data.model


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
)