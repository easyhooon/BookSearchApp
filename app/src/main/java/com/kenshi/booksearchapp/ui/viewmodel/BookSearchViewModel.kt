package com.kenshi.booksearchapp.ui.viewmodel

import androidx.lifecycle.*
import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.data.model.SearchResponse
import com.kenshi.booksearchapp.data.repository.BookSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//viewModel 그 자체로는 생성시에 초기값을 전달 받을 수 없다.
class BookSearchViewModel(
    private val bookSearchRepository: BookSearchRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // Api
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

    fun searchBooks(query: String) = viewModelScope.launch {
        //val response = bookSearchRepository.searchBooks(query, "accuracy", 1, 15)
        val response = bookSearchRepository.searchBooks(query, getSortMode(), 1, 15)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        }
    }

    // Room
    fun saveBooks(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.insertBooks(book)
    }

    fun deleteBooks(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.deleteBooks(book)
    }

    //val favoriteBooks: LiveData<List<Book>> = bookSearchRepository.getFavoriteBooks()
    //val favoriteBooks: Flow<List<Book>> = bookSearchRepository.getFavoriteBooks()
    val favoriteBooks: StateFlow<List<Book>> = bookSearchRepository.getFavoriteBooks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            listOf()
        )

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

    // DataStore is safe to call on UI thread too
//    fun saveSortMode(value: String) = viewModelScope.launch(Dispatchers.IO) {
//        bookSearchRepository.saveSortMode(value)
//    }

    fun saveSortMode(value: String) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.saveSortMode(value)
    }

    //withContext(Dispatchers.IO) 디스패처의 종류와는 상관없이 withContext 블럭은 반드시 값을 반환하고 종료한다고 한다
    //음 그러면 Dispatcher 어떻게 띠지
    suspend fun getSortMode() = withContext(Dispatchers.IO) {
        bookSearchRepository.getSortMode().first()
    }
}