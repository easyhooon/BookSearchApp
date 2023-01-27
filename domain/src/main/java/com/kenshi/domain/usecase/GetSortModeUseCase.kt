package com.kenshi.domain.usecase

import com.kenshi.booksearchapp.domain.BookSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSortModeUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    operator fun invoke(): Flow<String> {
        return bookSearchRepository.getSortMode()
    }
}