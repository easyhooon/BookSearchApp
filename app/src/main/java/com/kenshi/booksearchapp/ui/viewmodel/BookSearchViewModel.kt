package com.kenshi.booksearchapp.ui.viewmodel

import androidx.lifecycle.*
import com.kenshi.booksearchapp.data.model.SearchResponse
import com.kenshi.booksearchapp.data.repository.BookSearchRepository
import kotlinx.coroutines.launch

//viewModel 그 자체로는 생성시에 초기값을 전달 받을 수 없다.
class BookSearchViewModel(
    private val bookSearchRepository: BookSearchRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // Api
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

    //TODO 이거 dispatcher io로 바꾸셨던데 끝까지 안바꾸면 질문으로 태클걸어보자
    fun searchBooks(query: String) = viewModelScope.launch {
        val response = bookSearchRepository.searchBooks(query, "accuracy", 1, 15)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        }
    }

    //SaveState
    var query = String()
        set(value) {
            field = value
            savedStateHandle.set(SAVE_STATE_KEY, value)
        }

    init {
        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
    }

    companion object {
        private const val SAVE_STATE_KEY = "query"
    }
}