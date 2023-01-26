package com.kenshi.booksearchapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.domain.BookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _searchPagingResult = MutableStateFlow<PagingData<Book>>(PagingData.empty())
    val searchPagingResult: StateFlow<PagingData<Book>> = _searchPagingResult.asStateFlow()

    fun searchBooksPaging(query: String) {
        viewModelScope.launch {
            bookSearchRepository.searchBooks(query, getSortMode())
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
        bookSearchRepository.getSortMode().first()
    }

    companion object {
        private const val SAVE_STATE_KEY = "query"
    }
}