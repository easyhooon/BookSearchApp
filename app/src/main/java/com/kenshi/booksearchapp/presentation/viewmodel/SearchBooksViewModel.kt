package com.kenshi.booksearchapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kenshi.booksearchapp.domain.usecase.GetSortModeUseCase
import com.kenshi.booksearchapp.domain.usecase.SearchBooksUseCase
import com.kenshi.booksearchapp.presentation.item.BookItem
import com.kenshi.booksearchapp.presentation.mapper.toItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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