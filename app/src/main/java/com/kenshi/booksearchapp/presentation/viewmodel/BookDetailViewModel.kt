package com.kenshi.booksearchapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.booksearchapp.domain.usecase.GetFavoriteBooksForTestUseCase
import com.kenshi.booksearchapp.domain.usecase.InsertBookUseCase
import com.kenshi.booksearchapp.presentation.item.BookItem
import com.kenshi.booksearchapp.presentation.mapper.toDomain
import com.kenshi.booksearchapp.presentation.mapper.toItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val insertBookUseCase: InsertBookUseCase,
    private val getFavoriteBooksForTestUseCase: GetFavoriteBooksForTestUseCase
) : ViewModel() {

    fun saveBooks(bookItem: BookItem) = viewModelScope.launch {
        insertBookUseCase(bookItem.toDomain())
    }

    // For test
    // repository 의 값을 반환 받는 변수
    // viewModel 기능을 체크 하는데 repository 반환 값 그 자체에는 상관이 없기 때문에
    // repository fake double 을 사용
    val favoriteBooks: Flow<List<BookItem>> = getFavoriteBooksForTestUseCase().map {list ->
            list.map {bookEntity ->
                bookEntity.toItem()
            }
    }
}