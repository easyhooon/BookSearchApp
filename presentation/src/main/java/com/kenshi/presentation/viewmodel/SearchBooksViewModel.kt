package com.kenshi.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kenshi.domain.usecase.GetSortModeUseCase
import com.kenshi.domain.usecase.SearchBooksUseCase
import com.kenshi.presentation.item.BookItem
import com.kenshi.presentation.mapper.toItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchBooksViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
    private val getSortModeUseCase: GetSortModeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _searchPagingResult = MutableStateFlow<PagingData<BookItem>>(PagingData.empty())
    val searchPagingResult: StateFlow<PagingData<BookItem>> = _searchPagingResult.asStateFlow()

    // 함수의 파라미터가 존재 하므로 변수의 형태로 변환 할 수 없음
    fun searchBooksPaging(query: String) {
        viewModelScope.launch {
            searchBooksUseCase(query, getSortMode())
                .map { list ->
                    list.map { bookEntity ->
                        bookEntity.toItem()
                    }
                }
                .cachedIn(viewModelScope)
                .collect {
                    _searchPagingResult.value = it
                }
        }
    }

    var query = String()
        set(value) {
            field = value
            savedStateHandle[SAVE_STATE_KEY] = value
        }

    init {
        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
    }

    private suspend fun getSortMode() = withContext(viewModelScope.coroutineContext) {
        getSortModeUseCase().first()
    }

    companion object {
        private const val SAVE_STATE_KEY = "query"
    }
}