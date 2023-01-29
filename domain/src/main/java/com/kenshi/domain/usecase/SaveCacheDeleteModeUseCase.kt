package com.kenshi.domain.usecase

import com.kenshi.domain.repository.BookSearchRepository
import javax.inject.Inject

class SaveCacheDeleteModeUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    suspend operator fun invoke(mode: Boolean) {
        bookSearchRepository.saveCacheDeleteMode(mode)
    }
}
