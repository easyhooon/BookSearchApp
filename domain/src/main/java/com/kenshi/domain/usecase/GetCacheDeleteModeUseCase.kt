package com.kenshi.domain.usecase

import com.kenshi.booksearchapp.domain.BookSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCacheDeleteModeUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return bookSearchRepository.getCacheDeleteMode()
    }
}