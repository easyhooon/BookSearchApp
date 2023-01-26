package com.kenshi.booksearchapp.domain

import androidx.paging.PagingData
import com.kenshi.booksearchapp.domain.entity.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookSearchRepository {

//    suspend fun searchBooks(
//        query: String,
//        sort: String,
//        page: Int,
//        size: Int,
//    ): Response<SearchResponse>

    // Room
    suspend fun insertBook(bookEntity: BookEntity)

    suspend fun deleteBook(bookEntity: BookEntity)

    // fun getFavoriteBooks(): LiveData<List<Book>>
    fun getFavoriteBooksForTest(): Flow<List<BookEntity>>

    // DataStore
    suspend fun saveSortMode(mode: String)

    suspend fun getSortMode(): Flow<String>

    suspend fun saveCacheDeleteMode(mode: Boolean)

    suspend fun getCacheDeleteMode(): Flow<Boolean>

    // Paging
    fun getFavoriteBooks(): Flow<PagingData<BookEntity>>

    fun searchBooks(query: String, sort: String): Flow<PagingData<BookEntity>>
}