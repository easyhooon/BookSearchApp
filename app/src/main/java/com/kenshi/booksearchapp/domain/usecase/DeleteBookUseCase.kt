package com.kenshi.booksearchapp.domain.usecase

import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.domain.BookSearchRepository
import javax.inject.Inject

class DeleteBookUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    suspend operator fun invoke(book: Book) {
        bookSearchRepository.deleteBook(book)
    }
}