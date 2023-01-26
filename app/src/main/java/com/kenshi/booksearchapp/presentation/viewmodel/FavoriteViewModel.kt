package com.kenshi.booksearchapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kenshi.booksearchapp.domain.usecase.DeleteBookUseCase
import com.kenshi.booksearchapp.domain.usecase.GetFavoriteBooksUseCase
import com.kenshi.booksearchapp.domain.usecase.InsertBookUseCase
import com.kenshi.booksearchapp.presentation.item.BookItem
import com.kenshi.booksearchapp.presentation.mapper.toDomain
import com.kenshi.booksearchapp.presentation.mapper.toItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO 함수의 파라미터가 없을 경우엔 val 로 지정해주지 않아도 됨
// 해당 내용 관련 학습
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteBooksUseCase: GetFavoriteBooksUseCase,
    private val insertBookUseCase: InsertBookUseCase,
    private val deleteBookUseCase: DeleteBookUseCase
) : ViewModel() {

//    val favoriteBooks: StateFlow<List<Book>> = bookSearchRepository.getFavoriteBooks()
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000),
//            listOf()
//        )

    val favoriteBooks: StateFlow<PagingData<BookItem>> =
        getFavoriteBooksUseCase()
            .map {list ->
                list.map { bookEntity ->
                    bookEntity.toItem()
                }
            }
            // 코루틴이 데이터 스트림을 캐시하고 공유 가능하게 만들어줌
            .cachedIn(viewModelScope)
            // stateFlow 로 변환
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    fun saveBooks(bookItem: BookItem) = viewModelScope.launch {
        insertBookUseCase(bookItem.toDomain())
    }

    fun deleteBooks(bookItem: BookItem) = viewModelScope.launch {
        deleteBookUseCase(bookItem.toDomain())
    }
}