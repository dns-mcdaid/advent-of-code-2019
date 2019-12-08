package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 08")
class Day08Test {

  val exampleInput = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2)
  val exampleOutput = listOf(
    listOf(
      listOf(1, 2, 3),
      listOf(4, 5, 6)
    ),
    listOf(
      listOf(7, 8, 9),
      listOf(0, 1, 2)
    )
  )

  val input = Parser.read("day08_input.txt")

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    @Test
    fun `Example generates expected image`() {
      assertThat(Day08.buildImage(exampleInput, 3, 2)).isEqualTo(exampleOutput)
    }

    @Test
    fun `Example outputs expected map`() {
      val expectedMap = mapOf(
        7 to 1, 8 to 1, 9 to 1,
        0 to 1, 1 to 1, 2 to 1
      )

      assertThat(Day08.findLayerWithFewestZeros(exampleOutput)).isEqualTo(expectedMap)
    }

    @Test
    fun `Example outputs expected result`() {
      assertThat(Day08.execute(exampleInput, 3, 2)).isEqualTo(1)
    }

    @Test
    fun `Input generates acceptable result`() {
      val data = Day08.massageInput(input)
      println(Day08.execute(data, 25, 6))
    }
  }
}