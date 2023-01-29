package com.kenshi.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kenshi.presentation.R
import com.kenshi.presentation.base.BaseFragment
import com.kenshi.presentation.databinding.FragmentFavoriteBooksBinding
import com.kenshi.presentation.utils.collectLatestLifecycleFlow
import com.kenshi.presentation.viewmodel.FavoriteBooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteBooksFragment :
    BaseFragment<FragmentFavoriteBooksBinding>(R.layout.fragment_favorite_books) {

    private val favoriteBooksViewModel by viewModels<FavoriteBooksViewModel>()

    override fun getViewBinding() = FragmentFavoriteBooksBinding.inflate(layoutInflater)

    private lateinit var bookSearchAdapter: com.kenshi.presentation.adapter.BookSearchPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupTouchHelper(view)

        // pagingData 는 시간에 따라 변하기 때문에 collect 가 아닌 collectLatest 로 구독 처리
        // 기존의 paging 값을 cancel -> 새 값을 구독 하도록
        collectLatestLifecycleFlow(favoriteBooksViewModel.favoriteBooks) {
            bookSearchAdapter.submitData(it)
        }
    }

    private fun setupRecyclerView() {
        bookSearchAdapter = com.kenshi.presentation.adapter.BookSearchPagingAdapter()
        binding.rvFavoriteBooks.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = bookSearchAdapter
        }

        bookSearchAdapter.setOnItemClickListener { book ->
            val action =
                FavoriteBooksFragmentDirections.actionFragmentFavoriteBooksToFragmentBookDetail(book)
            findNavController().navigate(action)
        }
    }

    // 왼쪽으로 스와이프시 아이템 삭제
    private fun setupTouchHelper(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            // 스와이프시 저장했던 책을 제거
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                val pagedBook = bookSearchAdapter.peek(position)
                pagedBook?.let { book ->
                    favoriteBooksViewModel.deleteBook(book)
                    // undo 시 recyclerview 의 표시 위치를 유지 하고 싶으면
                    // book class 의 primary key 를 isbn 대신 자동 증가하는 정수값을 추가 해서 저장 해주면 됨
                    // 그러면 item 이 지워 졌다가 다시 생성 되어도 recyclerview 가 정수 오름차순으로 항목 값을 표시 해주게 됨
                    Snackbar.make(view, "Book has deleted", Snackbar.LENGTH_SHORT).apply {
                        setAction("Undo") {
                            favoriteBooksViewModel.saveBook(book)
                        }
                    }.show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavoriteBooks)
        }
    }
}
