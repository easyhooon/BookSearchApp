package com.kenshi.booksearchapp.data.repository

import androidx.paging.PagingData
import com.kenshi.booksearchapp.data.local.BookSearchLocalDataSource
import com.kenshi.booksearchapp.data.remote.BookSearchRemoteDataSource
import com.kenshi.booksearchapp.domain.BookSearchRepository
import com.kenshi.booksearchapp.domain.entity.BookEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class BookSearchRepositoryImpl @Inject constructor(
    private val bookSearchRemoteDataSource: BookSearchRemoteDataSource,
    private val bookSearchLocalDataSource: BookSearchLocalDataSource
) : BookSearchRepository {

    override fun searchBooks(query: String, sort: String): Flow<PagingData<BookEntity>> {
        return bookSearchRemoteDataSource.searchBooks(query, sort)
    }

    override suspend fun insertBook(bookEntity: BookEntity) {
        bookSearchLocalDataSource.insertBook(bookEntity)
    }

    override suspend fun deleteBook(bookEntity: BookEntity) {
        bookSearchLocalDataSource.insertBook(bookEntity)
    }

    override fun getFavoriteBooks(): Flow<PagingData<BookEntity>> {
        return bookSearchLocalDataSource.getFavoriteBooks()
    }

    override fun getFavoriteBooksForTest(): Flow<List<BookEntity>> {
        return bookSearchLocalDataSource.getFavoriteBooksForTest()
    }

    override suspend fun saveSortMode(mode: String) {
        bookSearchLocalDataSource.saveSortMode(mode)
    }

    override suspend fun getSortMode(): Flow<String> {
        return bookSearchLocalDataSource.getSortMode()
    }

    override suspend fun saveCacheDeleteMode(mode: Boolean) {
        bookSearchLocalDataSource.saveCacheDeleteMode(mode)
    }

    override suspend fun getCacheDeleteMode(): Flow<Boolean> {
        return bookSearchLocalDataSource.getCacheDeleteMode()
    }
}