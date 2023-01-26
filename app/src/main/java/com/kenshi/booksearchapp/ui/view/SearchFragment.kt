package com.kenshi.booksearchapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kenshi.booksearchapp.R
import com.kenshi.booksearchapp.common.collectLatestLifecycleFlow
import com.kenshi.booksearchapp.common.repeatOnStarted
import com.kenshi.booksearchapp.common.textChangesToFlow
import com.kenshi.booksearchapp.databinding.FragmentSearchBinding
import com.kenshi.booksearchapp.ui.adapter.BookSearchLoadStateAdapter
import com.kenshi.booksearchapp.ui.adapter.BookSearchPagingAdapter
import com.kenshi.booksearchapp.ui.base.BaseFragment
import com.kenshi.booksearchapp.ui.viewmodel.SearchViewModel
import com.kenshi.booksearchapp.util.Constants.SEARCH_BOOKS_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    //private lateinit var bookSearchViewModel: BookSearchViewModel
    //private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

    //private lateinit var bookSearchAdapter: BookSearchAdapter
    private lateinit var bookSearchAdapter: BookSearchPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        setupRecyclerView()
        //searchBooks()
        setupLoadState()
        initObservers()
    }

    @OptIn(FlowPreview::class)
    private fun initObservers() {
//        bookSearchViewModel.searchResult.observe(viewLifecycleOwner) { response ->
//            val books = response.documents
//            bookSearchAdapter.submitList(books)
//        }

//        lifecycle 처리
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                val editTextFlow = binding.etSearch.textChangesToFlow()
//
//                editTextFlow
//                    .debounce(SEARCH_BOOKS_TIME_DELAY)
//                    .filter {
//                        it?.length!! > 0
//                    }
//                    .onEach { text ->
//                        Log.d("editTextFlow", "$text")
//
//                        text?.let {
//                            val query = it.toString().trim()
//                            searchViewModel.searchBooksPaging(query)
//                            searchViewModel.query = query
//                        }
//                    }
//                    .launchIn(this)
//            }
//        }

        // 확장함수
        // 하나의 flow 에서 수명 주기 인식 수집을 진행하기만 하면 되는 경우엔 Flow.flowWithLifecycle 메서드를 사용하면
        // 코드가 단순해져서 좋음
        repeatOnStarted {
            val editTextFlow = binding.etSearch.textChangesToFlow()

            editTextFlow
                .debounce(SEARCH_BOOKS_TIME_DELAY)
                .filter {
                    it?.length!! > 0
                }
                .onEach { text ->
                    Log.d("editTextFlow", "$text")

                    text?.let {
                        val query = it.toString().trim()
                        searchViewModel.searchBooksPaging(query)
                        searchViewModel.query = query
                    }
                }
                .launchIn(this)

        }

        //TODO 왜 repeatOnLifecycle 이 아니고 collectLatest 를 사용했는지 확인
        collectLatestLifecycleFlow(searchViewModel.searchPagingResult) {
            bookSearchAdapter.submitData(it)
        }
    }

    private fun setupRecyclerView() {
        //bookSearchAdapter = BookSearchAdapter()
        bookSearchAdapter = BookSearchPagingAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL))
            //adapter = bookSearchAdapter

            // pagingDataAdapter 와 loadStateAdapter 연결
            // recyclerView 의 footer 로 error 상황과 retry button 이 나타남
            adapter = bookSearchAdapter.withLoadStateFooter(
                footer = BookSearchLoadStateAdapter(bookSearchAdapter::retry)
            )
        }

        bookSearchAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToFragmentBook(it)
            findNavController().navigate(action)
        }
    }

    // Flow Debounce 를 사용하는 방법으로 변경해보기
    private fun searchBooks() = with(binding) {
        var startTime = System.currentTimeMillis()
        var endTime: Long

        etSearch.text =
            Editable.Factory.getInstance().newEditable(searchViewModel.query)

        // 이런식으로 구현자체는 가능하다.
        etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if (endTime - startTime >= SEARCH_BOOKS_TIME_DELAY) {
                text?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        //bookSearchViewModel.searchBooks(query)
                        searchViewModel.searchBooksPaging(query)
                        searchViewModel.query = query
                    }
                }
            }
            startTime = endTime
        }
    }

    private fun setupLoadState() = with(binding) {
        //combinedLoadStates -> PagingSource 와 RemoteMediator 두가지 source 의 loading 상태를 가지고 있음
        //remoteMediator 는 사용하지 않기 때문에 source 의 값만 대응하면 됨
        //prepend : loading 시작시 만들어짐
        //append : loading 종료시 만들어짐
        //refresh : loading 값을 갱신할때 만들어짐
        bookSearchAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            // list가 비어있는지 판정하는 방법
            val isListEmpty = bookSearchAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            tvEmptylist.isVisible = isListEmpty
            rvSearchResult.isVisible = !isListEmpty

            progressBar.isVisible = loadState.refresh is LoadState.Loading

            //loading State 는 LoadStateAdapter 에서 관리해주기 때문에 주석처리
//            btnRetry.isVisible = loadState.refresh is LoadState.Error
//                    || loadState.append is LoadState.Error
//                    || loadState.prepend is LoadState.Error
//
//            val errorState: LoadState.Error? = loadState.append as? LoadState.Error
//                ?: loadState.prepend as? LoadState.Error
//                ?: loadState.refresh as? LoadState.Error
//            errorState?.let {
//                Toast.makeText(requireContext(), "it.error.message", Toast.LENGTH_SHORT).show()
//            }
//
//            btnRetry.setOnClickListener {
//                bookSearchAdapter.retry()
//            }
        }
    }
}