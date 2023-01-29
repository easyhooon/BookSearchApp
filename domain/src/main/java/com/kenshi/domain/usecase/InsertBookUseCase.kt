package com.kenshi.domain.usecase

import com.kenshi.domain.entity.BookEntity
import com.kenshi.domain.repository.BookSearchRepository
import javax.inject.Inject

class InsertBookUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    suspend operator fun invoke(bookEntity: BookEntity) {
        bookSearchRepository.insertBook(bookEntity)
    }
}
