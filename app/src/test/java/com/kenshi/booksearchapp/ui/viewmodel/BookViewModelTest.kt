package com.kenshi.booksearchapp.ui.viewmodel

import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.data.repository.FakeBookSearchRepository
import com.kenshi.booksearchapp.domain.usecase.GetFavoriteBooksForTestUseCase
import com.kenshi.booksearchapp.domain.usecase.InsertBookUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

// TODO 뷰모델이 aac 뷰모델인데 안드로이드 의존성이 없다고?
// 안드로이드 의존성이 존재하지 않기 때문에 Local Unit Test

// UI Layer + Data Layer 두 층에 걸친 동작을 테스트
// viewModel 밑 repository 동작 클래스
@MediumTest
class BookViewModelTest {

    private lateinit var viewModel: BookViewModel

    @Before
    fun setUp() {
        viewModel = BookViewModel(
            InsertBookUseCase(FakeBookSearchRepository()),
            GetFavoriteBooksForTestUseCase(FakeBookSearchRepository())
        )
        // viewModel = BookViewModel(FakeBookSearchRepository())
    }

    @After
    fun tearDown() {

    }
    
    @Test
    @ExperimentalCoroutinesApi
    fun save_book_test() = runTest {
        val book = Book(
            listOf("a"), "b", "c", "d", 0, "e",
            0, "f", "g", "h", listOf("i"),"j"
        )
        viewModel.saveBooks(book)
        
        val favoriteBooks = viewModel.favoriteBooks.first()
        assertThat(favoriteBooks).contains(book)
    }
}