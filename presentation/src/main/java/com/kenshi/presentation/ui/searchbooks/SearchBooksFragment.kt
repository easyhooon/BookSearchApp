package com.kenshi.presentation.ui.searchbooks

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kenshi.presentation.R
import com.kenshi.presentation.adapter.BookSearchLoadStateAdapter
import com.kenshi.presentation.adapter.BookSearchPagingAdapter
import com.kenshi.presentation.base.BaseFragment
import com.kenshi.presentation.databinding.FragmentSearchBooksBinding
import com.kenshi.presentation.utils.Constants.SEARCH_BOOKS_TIME_DELAY
import com.kenshi.presentation.utils.collectLatestLifecycleFlow
import com.kenshi.presentation.utils.repeatOnStarted
import com.kenshi.presentation.utils.textChangesToFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchBooksFragment :
    BaseFragment<FragmentSearchBooksBinding>(R.layout.fragment_search_books) {

    private val searchBooksViewModel by viewModels<SearchBooksViewModel>()

    override fun getViewBinding() = FragmentSearchBooksBinding.inflate(layoutInflater)

    private lateinit var bookSearchAdapter: BookSearchPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        // searchBooks()
        setupLoadState()
        initObservers()
    }

    private fun initObservers() {
        // 하나의 flow 에서 수명 주기 인식 수집을 진행 하기만 하면 되는 경우엔 Flow.flowWithLifecycle 메서드를 사용하면 됨
        repeatOnStarted {
            launch {
                val editTextFlow = binding.etSearch.textChangesToFlow()

                editTextFlow
                    .debounce(SEARCH_BOOKS_TIME_DELAY)
                    .filter {
                        it?.length!! > 0
                    }
                    .collect { text ->
                        text?.let {
                            val query = it.toString().trim()
                            // searchBooksViewModel.searchBooksPaging(query)
                            searchBooksViewModel.setQuery(query)
                        }
                    }
            }
        }

        collectLatestLifecycleFlow(searchBooksViewModel.searchBooks) {
            bookSearchAdapter.submitData(it)
        }
    }

    private fun setupRecyclerView() {
        bookSearchAdapter = BookSearchPagingAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            // pagingDataAdapter 와 loadStateAdapter 연결
            // recyclerView 의 footer 로 error 상황과 retry button 이 나타남
            adapter = bookSearchAdapter.withLoadStateFooter(
                footer = BookSearchLoadStateAdapter(
                    bookSearchAdapter::retry
                )
            )
        }

        bookSearchAdapter.setOnItemClickListener { book ->
            val action =
                SearchBooksFragmentDirections.actionFragmentSearchBooksToFragmentBookDetail(book)
            findNavController().navigate(action)
        }
    }

    // Flow Debounce 를 사용 하는 방법으로 변경 완료
    private fun searchBooks() = with(binding) {
        var startTime = System.currentTimeMillis()
        var endTime: Long

        etSearch.text = Editable.Factory.getInstance().newEditable(searchBooksViewModel.query.value)

        // 이런 식으로 구현 가능 하다.
        etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if (endTime - startTime >= SEARCH_BOOKS_TIME_DELAY) {
                text?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        // bookSearchViewModel.searchBooks(query)
                        // searchBooksViewModel.searchBooksPaging(query)
                        searchBooksViewModel.setQuery(query)
                    }
                }
            }
            startTime = endTime
        }
    }

    private fun setupLoadState() = with(binding) {
        // combinedLoadStates -> PagingSource 와 RemoteMediator 두가지 source 의 loading 상태를 가지고 있음
        // remoteMediator 는 사용 하지 않기 때문에 source 의 값만 대응 하면 됨
        // prepend : loading 시작시 만들어짐
        // append : loading 종료시 만들어짐
        // refresh : loading 값을 갱신할 때 만들어짐
        bookSearchAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            // list가 비어 있는지 판정 하는 방법
            val isListEmpty = bookSearchAdapter.itemCount < 1 &&
                loadState.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached

            tvEmptyList.isVisible = isListEmpty
            rvSearchResult.isVisible = !isListEmpty

            progressBar.isVisible = loadState.refresh is LoadState.Loading
        }
    }
}
