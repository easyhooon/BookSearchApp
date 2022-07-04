package com.kenshi.booksearchapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kenshi.booksearchapp.databinding.FragmentSearchBinding
import com.kenshi.booksearchapp.ui.adapter.BookSearchAdapter
import com.kenshi.booksearchapp.ui.viewmodel.BookSearchViewModel
import com.kenshi.booksearchapp.util.Constants.SEARCH_BOOKS_TIME_DELAY


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookSearchViewModel: BookSearchViewModel
    private lateinit var bookSearchAdapter: BookSearchAdapter

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
        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        setupRecyclerView()
        searchBooks()

        initObservers()
    }

    private fun initObservers() {
        bookSearchViewModel.searchResult.observe(viewLifecycleOwner) { response ->
            val books = response.documents
            bookSearchAdapter.submitList(books)
        }
    }

    private fun setupRecyclerView() {
        bookSearchAdapter = BookSearchAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL))
            adapter = bookSearchAdapter
        }
    }

    private fun searchBooks() = with(binding) {
        var startTime = System.currentTimeMillis()
        var endTime: Long

        etSearch.text =
            Editable.Factory.getInstance().newEditable(bookSearchViewModel.query)

        // 이런식으로 구현자체는 가능하다.
        etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if (endTime - startTime >= SEARCH_BOOKS_TIME_DELAY) {
                text?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        bookSearchViewModel.searchBooks(query)
                        bookSearchViewModel.query = query
                    }
                }
            }

            startTime = endTime

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}