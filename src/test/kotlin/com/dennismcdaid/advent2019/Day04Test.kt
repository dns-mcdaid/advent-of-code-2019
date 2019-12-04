package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 4")
class Day04Test {

  val inputStart = 147981
  val inputEnd = 691423

  @DisplayName("Part 1")
  @Nested
  inner class Part1 {

    @Test
    fun `toDigits generates expected list`() {
      assertThat(Day04.toDigits(123)).isEqualTo(listOf(1, 2, 3))
    }

    @Test
    fun `toNumber generates expected number`() {
      assertThat(Day04.toNumber(listOf(1, 2, 3))).isEqualTo(123)
    }

    @Test
    fun `Less than six digits is unacceptable`() {
      assertThat(Day04.matches(11)).isEqualTo(false)
    }

    @Test
    fun `Six digits is acceptable`() {
      assertThat(Day04.matches(111111)).isEqualTo(true)
    }

    @Test
    fun `Unsorted is unacceptable`() {
      assertThat(Day04.matches(223450)).isEqualTo(false)
    }

    @Test
    fun `Sorted is acceptable`() {
      assertThat(Day04.matches(111123)).isEqualTo(true)
    }

    @Test
    fun `No duplicates is unacceptable`() {
      assertThat(Day04.matches(123789)).isEqualTo(false)
    }

    @Test
    fun `Sorted duplicates is acceptable`() {
      assertThat(Day04.matches(123788)).isEqualTo(true)
    }

    @Test
    fun `Input generates expected result`() {
      assertThat(Day04.getPossiblePasswordCount(inputStart, inputEnd)).isEqualTo(1790)
    }
  }
}