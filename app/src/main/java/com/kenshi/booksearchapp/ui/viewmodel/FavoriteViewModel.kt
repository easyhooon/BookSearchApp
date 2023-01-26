package com.kenshi.booksearchapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.domain.BookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
) : ViewModel() {

//    val favoriteBooks: StateFlow<List<Book>> = bookSearchRepository.getFavoriteBooks()
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000),
//            listOf()
//        )

    val favoriteBooks: StateFlow<PagingData<Book>> =
        bookSearchRepository.getFavoriteBooks()
            // 코루틴이 데이터 스트림을 캐시하고 공유 가능하게 만들어줌
            .cachedIn(viewModelScope)
            // stateFlow 로 변환
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    fun saveBooks(book: Book) = viewModelScope.launch {
        bookSearchRepository.insertBook(book)
    }

    fun deleteBooks(book: Book) = viewModelScope.launch {
        bookSearchRepository.deleteBook(book)
    }
}