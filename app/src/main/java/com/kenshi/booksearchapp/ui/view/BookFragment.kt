package com.kenshi.booksearchapp.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kenshi.booksearchapp.R
import com.kenshi.booksearchapp.databinding.FragmentBookBinding
import com.kenshi.booksearchapp.ui.base.BaseFragment
import com.kenshi.booksearchapp.ui.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFragment : BaseFragment<FragmentBookBinding>(R.layout.fragment_book) {

    private val args by navArgs<BookFragmentArgs>()

    // private lateinit var bookSearchViewModel: BookSearchViewModel
    // private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val bookViewModel by viewModels<BookViewModel>()

    override fun getViewBinding() = FragmentBookBinding.inflate(layoutInflater)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 액티비티에서 뷰모델을 생성하던 작업을 이제 힐트가 해줌 필요 없어짐
        // bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        val book = args.book
        binding.webview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(book.url)
        }

        binding.fabFavorite.setOnClickListener {
            bookViewModel.saveBooks(book)
            Snackbar.make(view, "Book has saved", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        binding.webview.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.webview.onResume()
    }
}