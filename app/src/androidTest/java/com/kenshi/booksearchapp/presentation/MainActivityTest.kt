package com.kenshi.booksearchapp.presentation

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kenshi.presentation.R
import com.kenshi.presentation.adapter.BookSearchViewHolder
import com.kenshi.presentation.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test

// test runner 지정
// @RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

//    private lateinit var activityScenario: ActivityScenario<MainActivity>

//    @Before
//    fun setUp() {
//        // test core 에 activityScenario 를 사용하면
//        // test 용 activity 를 손쉽게 생성할 수 있음
//        activityScenario = ActivityScenario.launch(MainActivity::class.java)
//    }
//
//    @After
//    fun tearDown() {
//        activityScenario.close()
//    }

    // 매번 액티비티를 launch 하고 close 하는 건 번거롭기 때문에
    // 구글에서 activityScenario rule 을 만들었음
    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    @SmallTest
    fun test_Activity_State() {
        // val activityState = activityScenario.state.name
        val activityState = activityScenarioRule.scenario.state.name
        assertThat(activityState).isEqualTo("RESUMED")
    }

    // UI Test는 시간이 많이 걸리는 작업이므로 Large
    @Test
    @LargeTest
    fun from_SearchFragment_to_Favorite_Ui_Operation() {
        // 1. SearchFragment
        // 1-1) 리사이클러뷰 대신 '"No Result"'가 출력되는지 확인
        onView(withId(R.id.tv_emptylist))
            .check(matches(withText("No result")))
        // 1-2) 검색어로 '"android"'를 입력
        onView(withId(R.id.et_search))
            .perform(typeText("android"))
        onView(isRoot()).perform(waitFor(3000))
        // 1-3) 리사이클러뷰 표시를 확인
        onView(withId(R.id.rv_search_result))
            .check(matches(isDisplayed()))
        // 1-4) 첫번재 반환값을 클릭
        onView(withId(R.id.rv_search_result))
            .perform(actionOnItemAtPosition<BookSearchViewHolder>(0, click()))
        onView(isRoot()).perform(waitFor(1000))
        // 1-5) BookFragment 결과를 저장
        onView(withId(R.id.fab_favorite))
            .perform(click())
        // 1-6) 이전 화면으로 돌아감
        pressBack()
        // 1-7) SnackBar 가 사라질때까지 대기
        onView(isRoot()).perform(waitFor(3000))
        onView(withId(R.id.rv_search_result))
            .check(matches(isDisplayed()))

        // 2. FavoriteFragment
        // 2-1) FavoriteFragment 로 이동
        onView(withId(R.id.fragment_favorite_books))
            .perform(click())
        // 2-2) 리사이클러뷰 표시를 확인
        onView(withId(R.id.rv_favorite_books))
            .check(matches(isDisplayed()))
        // 2-3) 첫번째 아이템을 스와이프하여 삭제
        onView(withId(R.id.rv_favorite_books)).perform(
            actionOnItemAtPosition<BookSearchViewHolder>(0, swipeLeft())
        )
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String = "wait for $delay miliseconds"

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
