package com.kenshi.booksearchapp.domain.usecase

import androidx.paging.PagingData
import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.domain.BookSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    operator fun invoke(query: String, sort: String): Flow<PagingData<Book>> {
        return bookSearchRepository.searchBooks(query, sort)
    }
}