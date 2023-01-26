package com.kenshi.booksearchapp.domain.usecase

import com.kenshi.booksearchapp.domain.BookSearchRepository
import com.kenshi.booksearchapp.domain.entity.BookEntity
import javax.inject.Inject

class InsertBookUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    suspend operator fun invoke(bookEntity: BookEntity) {
        bookSearchRepository.insertBook(bookEntity)
    }
}