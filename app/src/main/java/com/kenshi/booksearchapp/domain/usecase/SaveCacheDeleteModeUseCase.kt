package com.kenshi.booksearchapp.domain.usecase

import com.kenshi.booksearchapp.domain.BookSearchRepository
import javax.inject.Inject

class SaveCacheDeleteModeUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    suspend operator fun invoke(mode: Boolean) {
        bookSearchRepository.saveCacheDeleteMode(mode)
    }
}