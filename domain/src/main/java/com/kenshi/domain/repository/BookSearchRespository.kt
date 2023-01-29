package com.kenshi.domain.repository

import androidx.paging.PagingData
import com.kenshi.domain.entity.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookSearchRepository {

    fun searchBooks(query: String, sort: String): Flow<PagingData<BookEntity>>

    suspend fun insertBook(bookEntity: BookEntity)

    suspend fun deleteBook(bookEntity: BookEntity)

    fun getFavoriteBooksForTest(): Flow<List<BookEntity>>

    fun getFavoriteBooks(): Flow<PagingData<BookEntity>>

    suspend fun saveSortMode(mode: String)

    fun getSortMode(): Flow<String>

    suspend fun saveCacheDeleteMode(mode: Boolean)

    suspend fun getCacheDeleteMode(): Flow<Boolean>
}
