package com.kenshi.presentation.ui.searchbooks

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchBooksViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
    private val getSortModeUseCase: GetSortModeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val query = savedStateHandle.getStateFlow<String?>(KEY_QUERY, null)

    private val searchSortMode: StateFlow<String> =
        getSortModeUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                ""
            )

    // TODO: 흐름 완벽히 이해
    val searchBooks: Flow<PagingData<BookItem>> =
        query.filterNotNull()
            .combineTransform(searchSortMode) { query, sortMode -> emit(query to sortMode) }
            .flatMapLatest { (query, sortMode) ->
                searchBooksUseCase(query, sortMode)
                    .map { pagingData ->
                        pagingData.map { bookEntity ->
                            bookEntity.toItem()
                        }
                    }
            }
            .cachedIn(viewModelScope)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                PagingData.empty()
            )

    fun setQuery(query: String) {
        savedStateHandle[KEY_QUERY] = query
    }

//    private val _searchPagingResult = MutableStateFlow<PagingData<BookEntity>>(PagingData.empty())
//    val searchPagingResult: StateFlow<PagingData<BookEntity>> = _searchPagingResult.asStateFlow()
//
//    fun searchBooksPaging(query: String) {
//        viewModelScope.launch {
//            searchBooksUseCase(query, getSortMode())
//                .map { pagingData ->
//                    pagingData.map { bookEntity ->
//                        bookEntity.toItem()
//                    }
//                }
//                .cachedIn(viewModelScope)
//                .collect {
//                    _searchPagingResult.value = it
//                }
//        }
//    }

    private suspend fun getSortMode() = viewModelScope.async {
        getSortModeUseCase().first()
    }.await()

//    private suspend fun getSortMode() = withContext(viewModelScope.coroutineContext) {
//        getSortModeUseCase().first()
//    }

    companion object {
        private const val KEY_QUERY = "query"
    }
}
