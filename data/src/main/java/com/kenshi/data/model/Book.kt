package com.kenshi.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Room DB Entity 로 사용
// sqlite 에서는 대소문자 구분을 하지 않음
// data 패키지의 Book data class 를 ui 에서 전달하거나 하지 않으므로 @Parcelize annotation 제거
@Keep
@JsonClass(generateAdapter = true)
@Entity(tableName = "books")
data class Book(
    @field:Json(name = "title")
    val title: String, // title: 도서 제목
    @field:Json(name = "contents")
    val contents: String, // contents: 도서 소개
    @field:Json(name = "url")
    val url: String, // url: 도서 상세 URL
    @PrimaryKey(autoGenerate = false)
    @field:Json(name = "isbn")
    val isbn: String, // isbn: 국제 표준 도서 번호, ISBN10(10자리) 또는 ISBN13(13자리) 형식
    @field:Json(name = "datetime")
    val datetime: String, // datetime: 도서 출판날짜, ISO 8601 형식
    @field:Json(name = "authors")
    val authors: List<String>, // authors: 도서 저자 리스트
    @field:Json(name = "publisher")
    val publisher: String, // publisher: 도서 출판사
    @field:Json(name = "translators")
    val translators: List<String>, // translators: 도서 번역자 리스트
    @field:Json(name = "price")
    val price: Int, // price: 도서 정가
    @ColumnInfo(name = "sale_price")
    @field:Json(name = "sale_price")
    val salePrice: Int, // sale_price: 도서 판매가
    @field:Json(name = "thumbnail")
    val thumbnail: String, // thumbnail: 도서 표지 미리보기 URL
    @field:Json(name = "status")
    val status: String, // status: 도서 판매 상태 정보(정상, 품절, 절판 등)
)