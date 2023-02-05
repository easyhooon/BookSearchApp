package com.kenshi.presentation.screen.favoritebooks

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

@HiltViewModel
class FavoriteBooksViewModel @Inject constructor(
    getFavoriteBooksUseCase: GetFavoriteBooksUseCase,
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
            // 코루틴스코프가 데이터 스트림을 캐시하고 공유 가능하게 만들어줌
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
