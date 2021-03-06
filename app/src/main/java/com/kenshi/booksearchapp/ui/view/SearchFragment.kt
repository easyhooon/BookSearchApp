package com.kenshi.booksearchapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kenshi.booksearchapp.common.collectLatestLifecycleFlow
import com.kenshi.booksearchapp.common.repeatOnStarted
import com.kenshi.booksearchapp.common.textChangesToFlow
import com.kenshi.booksearchapp.databinding.FragmentSearchBinding
import com.kenshi.booksearchapp.ui.adapter.BookSearchLoadStateAdapter
import com.kenshi.booksearchapp.ui.adapter.BookSearchPagingAdapter
import com.kenshi.booksearchapp.ui.viewmodel.SearchViewModel
import com.kenshi.booksearchapp.util.Constants.SEARCH_BOOKS_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    //    private lateinit var bookSearchViewModel: BookSearchViewModel
    //private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()

    //private lateinit var bookSearchAdapter: BookSearchAdapter
    private lateinit var bookSearchAdapter: BookSearchPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

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

//        lifecycle ??????
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

        // ????????????
        // ????????? flow ?????? ?????? ?????? ?????? ????????? ??????????????? ?????? ?????? ????????? Flow.flowWithLifecycle ???????????? ????????????
        // ????????? ??????????????? ??????
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

            // pagingDataAdapter ??? loadStateAdapter ??????
            // recyclerView ??? footer ??? error ????????? retry button ??? ?????????
            adapter = bookSearchAdapter.withLoadStateFooter(
                footer = BookSearchLoadStateAdapter(bookSearchAdapter::retry)
            )
        }

        bookSearchAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToFragmentBook(it)
            findNavController().navigate(action)
        }
    }

    // Flow Debounce ??? ???????????? ???????????? ???????????????
    private fun searchBooks() = with(binding) {
        var startTime = System.currentTimeMillis()
        var endTime: Long

        etSearch.text =
            Editable.Factory.getInstance().newEditable(searchViewModel.query)

        // ??????????????? ??????????????? ????????????.
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
        //combinedLoadStates -> PagingSource ??? RemoteMediator ????????? source ??? loading ????????? ????????? ??????
        //remoteMediator ??? ???????????? ?????? ????????? source ??? ?????? ???????????? ???
        //prepend : loading ????????? ????????????
        //append : loading ????????? ????????????
        //refresh : loading ?????? ???????????? ????????????
        bookSearchAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            // list??? ??????????????? ???????????? ??????
            val isListEmpty = bookSearchAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            tvEmptylist.isVisible = isListEmpty
            rvSearchResult.isVisible = !isListEmpty

            progressBar.isVisible = loadState.refresh is LoadState.Loading

            //loading State ??? LoadStateAdapter ?????? ??????????????? ????????? ????????????
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


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}