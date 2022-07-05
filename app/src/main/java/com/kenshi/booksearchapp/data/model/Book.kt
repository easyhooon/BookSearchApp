package com.kenshi.booksearchapp.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

//Room DB Entity 로 사용
//sqlite 에서는 대소문자 구분을 하지 않음
@Parcelize
@Keep
@JsonClass(generateAdapter = true)
@Entity(tableName = "books")
data class Book(
    val authors: List<String>,
    val contents: String,
    val datetime: String,
    @PrimaryKey(autoGenerate = false)
    val isbn: String,
    val price: Int,
    val publisher: String,
    @ColumnInfo(name = "sale_price")
    @field:Json(name = "sale_price")
    val salePrice: Int,
    val status: String,
    val thumbnail: String,
    val title: String,
    val translators: List<String>,
    val url: String,
) : Parcelable
