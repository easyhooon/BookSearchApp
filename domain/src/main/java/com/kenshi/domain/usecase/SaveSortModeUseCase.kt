package com.kenshi.domain.usecase

import com.kenshi.domain.repository.BookSearchRepository
import javax.inject.Inject

class SaveSortModeUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    suspend operator fun invoke(mode: String) {
        bookSearchRepository.saveSortMode(mode)
    }
}
