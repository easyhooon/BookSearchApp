package com.kenshi.presentation.screen.bookdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kenshi.presentation.R
import com.kenshi.presentation.base.BaseFragment
import com.kenshi.presentation.databinding.FragmentBookDetailBinding
import com.kenshi.presentation.screen.BookDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>(R.layout.fragment_book_detail) {

    private val args by navArgs<BookDetailFragmentArgs>()

    private val bookDetailViewModel by viewModels<BookDetailViewModel>()

    override fun getViewBinding() = FragmentBookDetailBinding.inflate(layoutInflater)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val book = args.book
        binding.webview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(book.url)
        }

        binding.fabFavorite.setOnClickListener {
            bookDetailViewModel.saveBooks(book)
            Snackbar.make(view, getString(R.string.book_has_saved), Snackbar.LENGTH_SHORT).show()
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
