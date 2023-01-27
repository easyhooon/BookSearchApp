package com.kenshi.booksearchapp.util

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

// 안드로이드 의존성이 없는 테스트 이므로 UnitTest 에 위치
@SmallTest
class CalculatorTest {
    // 인스턴스 생성
    // 테스트 시에는 각각의 독립된 Calculator 객체를 사용 해야 함
    lateinit var calculator: Calculator

    // 테스트 실행 직전에 수행 해야할 작업을 해줄 수 있음
    // 각각의 테스트에서 사용할 객체를 생성
    @Before
    fun setup() {
        calculator = Calculator()
    }

    // 테스트 직후 해야 하는 작업, db를 닫거나, 객체를 삭제
    @After
    fun tearDown() {

    }

    // 메소드 이름을 문장 처럼 정의
    @Test
    fun `additional function test`() {
        // Given
        val x = 4
        val y = 2

        //When
        val result = calculator.addition(x, y)

        //Then
        assertThat(result).isEqualTo(6)
    }

    // 메소드 이름을 문장 처럼 정의
    @Test
    fun `subtraction function test`() {
        // Given
        val x = 4
        val y = 2

        //When
        val result = calculator.subtraction(x, y)

        //Then
        assertThat(result).isEqualTo(2)
    }

    // 메소드 이름을 문장 처럼 정의
    @Test
    fun `multiplication function test`() {
        // Given
        val x = 4
        val y = 2

        // When
        val result = calculator.multiplication(x, y)

        // Then
        assertThat(result).isEqualTo(8)
    }

    // 메소드 이름을 문장 처럼 정의
    @Test
    fun `division function test`() {
        // Given
        val x = 4
        val y = 0

        // When
        val result = calculator.division(x, y)

        // Then
        assertThat(result).isEqualTo(null)
    }
}