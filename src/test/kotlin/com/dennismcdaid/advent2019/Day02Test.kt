package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Day 02")
class Day02Test {

  private val input = Parser.readInts("day02_input.txt")

  private val smallestTestData = intArrayOf(1, 0, 0, 0, 99)
  private val testData = listOf(
    intArrayOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50) to intArrayOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50),
    intArrayOf(1, 0, 0, 0, 99) to intArrayOf(2, 0, 0, 0, 99),
    intArrayOf(2, 3, 0, 3, 99) to intArrayOf(2, 3, 0, 6, 99),
    intArrayOf(2, 4, 4, 5, 99, 0) to intArrayOf(2, 4, 4, 5, 99, 9801),
    intArrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99) to intArrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99)
  )

  @Test
  fun `Mutates test data to expected output`() {
    testData.forEach { (array, expected) ->
      assertThat(Day02.firstRegisterAfterMutation(array)).isEqualTo(expected[0])
    }
  }

  @Test
  fun `Generates expected solution for Part 1`() {
    val brokenFirstRegister = Day02.manipulateData(input)
      .let(Day02::firstRegisterAfterMutation)

    assertThat(brokenFirstRegister).isEqualTo(5534943)
  }

  @Test
  fun `Noun and verb mutation is determined successfully`() {
    assertThat(Day02.getNounVerbMutated(input, 19690720)).isEqualTo(7603)
  }
}