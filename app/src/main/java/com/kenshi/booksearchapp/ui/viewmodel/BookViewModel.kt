package com.kenshi.booksearchapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.repository.BookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
) : ViewModel() {

    // Room
    fun saveBooks(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.insertBooks(book)
    }

    // For test
    // repository 의 값을 반환 받는 변수
    // viewModel 기능을 체크하는데 repository 반환 값 그 자체에는 상관이 없기 때문에
    // repository fake double 을 사용
    val favoriteBooks: Flow<List<Book>> = bookSearchRepository.getFavoriteBooks()
}