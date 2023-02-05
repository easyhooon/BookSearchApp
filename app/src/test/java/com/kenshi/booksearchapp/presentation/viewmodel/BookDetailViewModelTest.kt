package com.kenshi.booksearchapp.presentation.viewmodel

import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.kenshi.booksearchapp.data.repository.FakeBookSearchRepository
import com.kenshi.domain.usecase.GetFavoriteBooksForTestUseCase
import com.kenshi.domain.usecase.InsertBookUseCase
import com.kenshi.presentation.item.BookItem
import com.kenshi.presentation.ui.bookdetail.BookDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

// TODO ViewModel 이 aac ViewModel 인데 안드로이드 의존성이 없다고?
// 안드로이드 의존성이 존재하지 않기 때문에 Local Unit Test

// UI Layer + Data Layer 두 층에 걸친 동작을 테스트
// viewModel 밑 repository 동작 클래스
@MediumTest
class BookDetailViewModelTest {

    private lateinit var viewModel: BookDetailViewModel

    @Before
    fun setUp() {
        viewModel = BookDetailViewModel(
            InsertBookUseCase(FakeBookSearchRepository()),
            GetFavoriteBooksForTestUseCase(FakeBookSearchRepository())
        )
        // viewModel = BookViewModel(FakeBookSearchRepository())
    }

    @After
    fun tearDown() {}

    @Test
    @ExperimentalCoroutinesApi
    fun save_book_test() = runTest {
        val book = BookItem(
            "a", "b", "c", "d", "e", listOf("f"),
            "g", listOf("h"), 0, 0, "i", "j"
        )
        viewModel.saveBooks(book)

        val favoriteBooks = viewModel.favoriteBooks.first()
        assertThat(favoriteBooks).contains(book)
    }
}
