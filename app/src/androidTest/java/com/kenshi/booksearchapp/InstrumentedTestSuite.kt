package com.kenshi.booksearchapp

import com.kenshi.booksearchapp.data.db.BookSearchDaoTest
import com.kenshi.booksearchapp.ui.view.MainActivityTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

// 테스트 자동화
@RunWith(Suite::class)
@ExperimentalCoroutinesApi
//실행하고싶은 순서대로 넣어줌
@Suite.SuiteClasses(
    MainActivityTest::class,
    BookSearchDaoTest::class
)
class InstrumentedTestSuite