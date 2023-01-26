package com.kenshi.booksearchapp.domain

import androidx.paging.PagingData
import com.kenshi.booksearchapp.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookSearchRepository {

//    suspend fun searchBooks(
//        query: String,
//        sort: String,
//        page: Int,
//        size: Int,
//    ): Response<SearchResponse>

    // Room
    suspend fun insertBook(book: Book)

    suspend fun deleteBook(book: Book)

    // fun getFavoriteBooks(): LiveData<List<Book>>
    fun getFavoriteBooksForTest(): Flow<List<Book>>

    // DataStore
    suspend fun saveSortMode(mode: String)

    suspend fun getSortMode(): Flow<String>

    suspend fun saveCacheDeleteMode(mode: Boolean)

    suspend fun getCacheDeleteMode(): Flow<Boolean>

    // Paging
    fun getFavoriteBooks(): Flow<PagingData<Book>>

    fun searchBooks(query: String, sort: String): Flow<PagingData<Book>>
}