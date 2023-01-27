package com.kenshi.booksearchapp.presentation.screen

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kenshi.presentation.screen.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// roboretric 을 사용하여 테스트하는 경우 jvm 위에서 테스트를 돌리기 때문에 local -> Unit test 폴더에서

// @RunWith(RobolectricTestRunner::class)
// class MainActivityTest {
//
//     @Test
//     @SmallTest
//     fun test_activity_state() {
//         // 3.0 까지 지원 되었던 메소드
//         // 4.0 부터 roboretrics androidX Test에 통합됨
//         // android JUnit4 Runner 가 local test 인지 계측 테스트(Instrumented Test)인지 알아서 판단
//         // 계측 테스트로 용으로 작성 하였던 코드를 그대로 로컬 테스트에서 사용할 수 있다는 의미
//         val controller = Robolectric.setupActivity(MainActivity::class.java)
//         val activityState = controller.lifecycle.currentState.name
//         assertThat(activityState).isEqualTo("RESUMED")
//     }
// }

// 계측 테스트 코드를 그대로 local test로 수행하여 더 빠르게 테스트를 진행할 수 있음!
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    @SmallTest
    fun test_activity_state() {
        //val activityState = activityScenario.state.name
        val activityState = activityScenarioRule.scenario.state.name
        assertThat(activityState).isEqualTo("RESUMED")
    }
}