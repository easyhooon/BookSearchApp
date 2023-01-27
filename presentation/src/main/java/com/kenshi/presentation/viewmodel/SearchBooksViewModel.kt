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

    val query = savedStateHandle.getStateFlow(KEY_QUERY, "")

    private val _searchPagingResult = MutableStateFlow<PagingData<BookItem>>(PagingData.empty())
    val searchPagingResult: StateFlow<PagingData<BookItem>> = _searchPagingResult.asStateFlow()

    // 함수의 파라미터가 존재 하므로 변수의 형태로 변환 할 수 없음
    // 변수에 전달을 안하는데 어떻게 실행되고 있는거지
    fun searchBooksPaging(query: String) {
        viewModelScope.launch {
            searchBooksUseCase(query, getSortMode())
                .map { pagingData ->
                    pagingData.map { bookEntity ->
                        bookEntity.toItem()
                    }
                }
                // 여기까지 변환된 데이터의 타입은 Flow<PagingData<BookItem>>
                // 내가 받으려는 타입은 PagingData<BookItem>
                .cachedIn(viewModelScope)
                .collect {
                    _searchPagingResult.value = it
                }
        }
    }

    fun setQuery(query: String) {
        savedStateHandle[KEY_QUERY] = query
    }

    private suspend fun getSortMode() = withContext(viewModelScope.coroutineContext) {
        getSortModeUseCase().first()
    }

    companion object {
        private const val KEY_QUERY = "query"
    }
}