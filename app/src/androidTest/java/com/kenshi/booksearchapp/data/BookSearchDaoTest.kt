package com.kenshi.booksearchapp.data

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kenshi.data.source.local.BookSearchDao
import com.kenshi.data.source.local.db.BookSearchDatabase
import com.kenshi.data.model.Book
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

// @RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class BookSearchDaoTest {

    // private lateinit var database: BookSearchDatabase
    @Inject
    @Named("test_db")
    lateinit var database: BookSearchDatabase
    private lateinit var dao: BookSearchDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // test 간의 고립성을 위해
    @Before
    fun setUp() {
        // memory 안에서만 데이터베이스를 생성
//        database = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            BookSearchDatabase::class.java
//        //ANR(Application Not Responding) 이 발생하지 않도록 Room에서는 메인스레드에서의 쿼리를 허용하지 않음
//        //테스트를 위해 메인스레드에서의 쿼리를 허용
//        ).allowMainThreadQueries().build()

        // setUp 에서 inject 메소드를 실행해주면 hilt 가 주입이 필요한 모든 의존성을 추가해줌
        hiltRule.inject()
        dao = database.bookSearchDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    // suspend fun test
    // coroutine test 는 runTest 블록 안에서 해주면 됨
    @Test
    fun insert_book_to_db() = runTest {
        val book = Book(
            "a", "b", "c", "d", "e", listOf("f"),
            "g", listOf("h"), 0, 0, "i", "j"
        )
        dao.insertBook(book)

        // 반환값이 flow 형태이기 때문에 first 로 value 의 형태로
        // liveData 와 대비해서 간편하게 테스트가 가능
        val favoriteBooks = dao.getFavoriteBooks().first()
        assertThat(favoriteBooks).contains(book)
    }

    @Test
    fun delete_book_to_db() = runTest {
        val book = Book(
            "a", "b", "c", "d", "e", listOf("f"),
            "g", listOf("h"), 0, 0, "i", "j"
        )
        dao.insertBook(book)
        dao.deleteBook(book)

        // delete method 를 테스트하는 로직 확인
        val favoriteBooks = dao.getFavoriteBooks().first()
        assertThat(favoriteBooks).doesNotContain(book)
    }
}
