package com.kenshi.booksearchapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.domain.usecase.DeleteBookUseCase
import com.kenshi.booksearchapp.domain.usecase.GetFavoriteBooksUseCase
import com.kenshi.booksearchapp.domain.usecase.InsertBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    val favoriteBooks: StateFlow<PagingData<Book>> =
        getFavoriteBooksUseCase()
            // 코루틴이 데이터 스트림을 캐시하고 공유 가능하게 만들어줌
            .cachedIn(viewModelScope)
            // stateFlow 로 변환
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    fun saveBooks(book: Book) = viewModelScope.launch {
        insertBookUseCase(book)
    }

    fun deleteBooks(book: Book) = viewModelScope.launch {
        deleteBookUseCase(book)
    }
}