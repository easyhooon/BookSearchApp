package com.kenshi.domain.usecase

import androidx.paging.PagingData
import com.kenshi.domain.entity.BookEntity
import com.kenshi.domain.repository.BookSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteBooksUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    operator fun invoke(): Flow<PagingData<BookEntity>> {
        return bookSearchRepository.getFavoriteBooks()
    }
}
