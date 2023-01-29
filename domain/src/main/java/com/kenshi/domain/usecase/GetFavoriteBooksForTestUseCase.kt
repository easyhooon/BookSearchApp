package com.kenshi.domain.usecase

import com.kenshi.domain.entity.BookEntity
import com.kenshi.domain.repository.BookSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteBooksForTestUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    operator fun invoke(): Flow<List<BookEntity>> {
        return bookSearchRepository.getFavoriteBooksForTest()
    }
}
