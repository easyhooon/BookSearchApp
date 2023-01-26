package com.kenshi.booksearchapp.data.repository

import androidx.paging.PagingData
import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.domain.BookSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

//implements 할때 shift + 방향키 아래
// test 용 (프로덕션에는 사용할 수 없는) Fake Double 구현
class FakeBookSearchRepository : BookSearchRepository {

    private val bookItems = mutableListOf<Book>()

//    override suspend fun searchBooks(
//        query: String,
//        sort: String,
//        page: Int,
//        size: Int
//    ): Response<SearchResponse> {
//        TODO("Not yet implemented")
//    }

    override suspend fun insertBook(book: Book) {
        bookItems.add(book)
    }

    override suspend fun deleteBook(book: Book) {
        bookItems.remove(book)
    }

    // bookItems 의 내용을 flow 형태 로 반환
     override fun getFavoriteBooksForTest(): Flow<List<Book>> = flowOf(bookItems)

    override suspend fun saveSortMode(mode: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getSortMode(): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun saveCacheDeleteMode(mode: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getCacheDeleteMode(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteBooks(): Flow<PagingData<Book>> {
        TODO("Not yet implemented")
    }

    override fun searchBooks(query: String, sort: String): Flow<PagingData<Book>> {
        TODO("Not yet implemented")
    }
}