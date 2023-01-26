package com.kenshi.booksearchapp.domain.usecase

import androidx.paging.PagingData
import com.kenshi.booksearchapp.domain.BookSearchRepository
import com.kenshi.booksearchapp.domain.entity.BookEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteBooksUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    operator fun invoke(): Flow<PagingData<BookEntity>> {
        return bookSearchRepository.getFavoriteBooks()
    }
}
