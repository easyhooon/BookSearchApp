package com.kenshi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kenshi.domain.usecase.DeleteBookUseCase
import com.kenshi.domain.usecase.GetFavoriteBooksUseCase
import com.kenshi.domain.usecase.InsertBookUseCase
import com.kenshi.presentation.item.BookItem
import com.kenshi.presentation.mapper.toEntity
import com.kenshi.presentation.mapper.toItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO 생성자 주입시 주입한 클래스의 파라미터가 없을 경우엔 생성자를 val 로 지정해주지 않아도 됨
// 해당 내용 관련 학습
@HiltViewModel
class FavoriteBooksViewModel @Inject constructor(
    private val getFavoriteBooksUseCase: GetFavoriteBooksUseCase,
    private val insertBookUseCase: InsertBookUseCase,
    private val deleteBookUseCase: DeleteBookUseCase
) : ViewModel() {

    val favoriteBooks: StateFlow<PagingData<BookItem>> =
        getFavoriteBooksUseCase()
            .map { pagingData ->
                pagingData.map { bookEntity ->
                    bookEntity.toItem()
                }
            }
            // 코루틴이 데이터 스트림을 캐시하고 공유 가능하게 만들어줌
            .cachedIn(viewModelScope)
            // stateFlow 로 변환
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    fun saveBook(bookItem: BookItem) = viewModelScope.launch {
        insertBookUseCase(bookItem.toEntity())
    }

    fun deleteBook(bookItem: BookItem) = viewModelScope.launch {
        deleteBookUseCase(bookItem.toEntity())
    }
}
