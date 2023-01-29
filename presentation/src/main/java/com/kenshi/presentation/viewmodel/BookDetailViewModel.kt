package com.kenshi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.domain.usecase.GetFavoriteBooksForTestUseCase
import com.kenshi.domain.usecase.InsertBookUseCase
import com.kenshi.presentation.item.BookItem
import com.kenshi.presentation.mapper.toEntity
import com.kenshi.presentation.mapper.toItem
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
        insertBookUseCase(bookItem.toEntity())
    }

    // For test
    // repository 의 값을 반환 받는 변수
    // viewModel 기능을 체크 하는데 repository 반환 값 그 자체에는 상관이 없기 때문에
    // repository fake double 을 사용
    val favoriteBooks: Flow<List<BookItem>> = getFavoriteBooksForTestUseCase().map { list ->
        list.map { bookEntity ->
            bookEntity.toItem()
        }
    }
}
