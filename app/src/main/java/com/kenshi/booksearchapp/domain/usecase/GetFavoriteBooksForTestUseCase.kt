package com.kenshi.booksearchapp.domain.usecase

import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.domain.BookSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteBooksForTestUseCase @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) {
    operator fun invoke(): Flow<List<Book>> {
        return bookSearchRepository.getFavoriteBooksForTest()
    }
}