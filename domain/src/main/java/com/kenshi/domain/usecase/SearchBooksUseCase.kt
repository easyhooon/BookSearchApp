package com.kenshi.domain.usecase

import androidx.paging.PagingData
import com.kenshi.domain.entity.BookEntity
import com.kenshi.domain.repository.BookSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    operator fun invoke(query: String, sort: String): Flow<PagingData<BookEntity>> {
        return bookSearchRepository.searchBooks(query, sort)
    }
}
