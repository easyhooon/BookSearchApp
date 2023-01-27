package com.kenshi.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.kenshi.booksearchapp.domain.BookSearchRepository
import com.kenshi.data.local.BookSearchLocalDataSource
import com.kenshi.data.mapper.toEntity
import com.kenshi.data.remote.BookSearchRemoteDataSource
import com.kenshi.domain.entity.BookEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class BookSearchRepositoryImpl @Inject constructor(
    private val bookSearchRemoteDataSource: BookSearchRemoteDataSource,
    private val bookSearchLocalDataSource: BookSearchLocalDataSource
) : BookSearchRepository {

    override fun searchBooks(query: String, sort: String): Flow<PagingData<BookEntity>> {
        return bookSearchRemoteDataSource.searchBooks(query, sort).map { pagingData ->
            pagingData.map { book ->
                book.toEntity()
            }
        }
    }

    override suspend fun insertBook(bookEntity: BookEntity) {
        bookSearchLocalDataSource.insertBook(bookEntity)
    }

    override suspend fun deleteBook(bookEntity: BookEntity) {
        bookSearchLocalDataSource.deleteBook(bookEntity)
    }

    override fun getFavoriteBooks(): Flow<PagingData<BookEntity>> {
        return bookSearchLocalDataSource.getFavoriteBooks().map { pagingData ->
            pagingData.map { book ->
                book.toEntity()
            }
        }
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