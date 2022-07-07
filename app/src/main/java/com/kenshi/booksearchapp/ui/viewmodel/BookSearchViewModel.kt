package com.kenshi.booksearchapp.ui.viewmodel

// 관심사 분리

//viewModel 그 자체로는 생성시에 초기값을 전달 받을 수 없다.
//@HiltViewModel
//class BookSearchViewModel @Inject constructor(
//    private val bookSearchRepository: BookSearchRepository,
//    private val workManager: WorkManager,
//    private val savedStateHandle: SavedStateHandle,
//) : ViewModel() {

//    // Api
//    private val _searchResult = MutableLiveData<SearchResponse>()
//    val searchResult: LiveData<SearchResponse> get() = _searchResult
//
//    fun searchBooks(query: String) = viewModelScope.launch {
//        //val response = bookSearchRepository.searchBooks(query, "accuracy", 1, 15)
//        val response = bookSearchRepository.searchBooks(query, getSortMode(), 1, 15)
//        if (response.isSuccessful) {
//            response.body()?.let { body ->
//                _searchResult.postValue(body)
//            }
//        }
//    }

// Room
//    fun saveBooks(book: Book) = viewModelScope.launch(Dispatchers.IO) {
//        bookSearchRepository.insertBooks(book)
//    }

//    fun deleteBooks(book: Book) = viewModelScope.launch(Dispatchers.IO) {
//        bookSearchRepository.deleteBooks(book)
//    }

//val favoriteBooks: LiveData<List<Book>> = bookSearchRepository.getFavoriteBooks()
//val favoriteBooks: Flow<List<Book>> = bookSearchRepository.getFavoriteBooks()
//    val favoriteBooks: StateFlow<List<Book>> = bookSearchRepository.getFavoriteBooks()
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000),
//            listOf()
//        )

//    //SaveState
//    var query = String()
//        set(value) {
//            field = value
//            savedStateHandle.set(SAVE_STATE_KEY, value)
//        }
//
//    init {
//        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
//    }

//    companion object {
//        private const val SAVE_STATE_KEY = "query"
//
//        //WorkManager 작업의 tag 로 사용
//        private val WORKER_KEY = "cache_worker"
//    }

//    // DataStore
//    // DataStore is safe to call on UI thread too
////    fun saveSortMode(value: String) = viewModelScope.launch(Dispatchers.IO) {
////        bookSearchRepository.saveSortMode(value)
////    }
//
//    fun saveSortMode(value: String) = viewModelScope.launch {
//        bookSearchRepository.saveSortMode(value)
//    }
//
//    // 설정 값 특성상 전체 데이터 스트림을 구독할 필요x
//    // flow 에서 단일 스트링값을 가져오기 위해 .first()
//    // withContext(Dispatchers.IO) 디스패처의 종류와는 상관없이 withContext 블럭은 반드시 값을 반환하고 종료한다고 한다
//    // 음 그러면 Dispatcher 어떻게 띠지
////    suspend fun getSortMode() = withContext(Dispatchers.IO) {
////        bookSearchRepository.getSortMode().first()
////    }
//
//    suspend fun getSortMode() = withContext(viewModelScope.coroutineContext) {
//        bookSearchRepository.getSortMode().first()
//    }
//
//    fun saveCacheDeleteMode(value: Boolean) = viewModelScope.launch {
//        bookSearchRepository.saveCacheDeleteMode(value)
//    }
//
//    suspend fun getCacheDeleteMode() = withContext(viewModelScope.coroutineContext) {
//        bookSearchRepository.getCacheDeleteMode().first()
//    }

//    // Paging
//    val favoritePagingBooks: StateFlow<PagingData<Book>> =
//        bookSearchRepository.getFavoritePagingBooks()
//            //코투린이 데이터 스트림을 캐시하고 공유가능하게 만들어줌
//            .cachedIn(viewModelScope)
//            //stateFlow 로 변환
//            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

//    private val _searchPagingResult = MutableStateFlow<PagingData<Book>>(PagingData.empty())
//
//    //ui 에는 변경 불가능한 searchPagingResult 를 공개
//    val searchPagingResult: StateFlow<PagingData<Book>> = _searchPagingResult.asStateFlow()
//
//    fun searchBooksPaging(query: String) {
//        viewModelScope.launch {
//            bookSearchRepository.searchBooksPaging(query, getSortMode())
//                .cachedIn(viewModelScope)
//                .collect {
//                    _searchPagingResult.value = it
//                }
//        }
//    }

//    // WorkManager
//    fun setWork() {
//        val constraints = Constraints.Builder()
//            //충전중일때만
//            .setRequiresCharging(true)
//            //배터리 잔량 충분
//            .setRequiresBatteryNotLow(true)
//            .build()
//
//        // 15분에 한번식 work
//        val workRequest = PeriodicWorkRequestBuilder<CacheDeleteWorker>(15, TimeUnit.MINUTES)
//            //constraints 반영
//            .setConstraints(constraints)
//            .build()
//
//        workManager.enqueueUniquePeriodicWork(
//            WORKER_KEY, ExistingPeriodicWorkPolicy.REPLACE, workRequest
//        )
//    }
//
//    fun deleteWork() = workManager.cancelUniqueWork(WORKER_KEY)
//
//    //현재 work 의 상태를 liveData type 으로 반환
//    fun getWorkStatus(): LiveData<MutableList<WorkInfo>> =
//        workManager.getWorkInfosForUniqueWorkLiveData(WORKER_KEY)
//}