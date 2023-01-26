package com.kenshi.booksearchapp.domain

import androidx.paging.PagingData
import com.kenshi.booksearchapp.domain.entity.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookSearchRepository {

    fun searchBooks(query: String, sort: String): Flow<PagingData<BookEntity>>

    suspend fun insertBook(bookEntity: BookEntity)

    suspend fun deleteBook(bookEntity: BookEntity)

    fun getFavoriteBooksForTest(): Flow<List<BookEntity>>

    fun getFavoriteBooks(): Flow<PagingData<BookEntity>>

    suspend fun saveSortMode(mode: String)

    suspend fun getSortMode(): Flow<String>

    suspend fun saveCacheDeleteMode(mode: Boolean)

    suspend fun getCacheDeleteMode(): Flow<Boolean>
}