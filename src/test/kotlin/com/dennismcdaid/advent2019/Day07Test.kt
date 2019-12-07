package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 07")
class Day07Test {

  val input = Parser.readInts("day07_input.txt")

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    @Test
    fun `Run on amplifier produces the same output as day 5`() {
      val day05input = Parser.readInts("day05_input.txt")
      assertThat(Day07.runOnAmplifier(day05input.copyOf(), listOf(1))).isEqualTo(12896948)
      assertThat(Day07.runOnAmplifier(day05input.copyOf(), listOf(5))).isEqualTo(7704130)
    }

    @Test
    fun `Unique sequences are generated`() {
      val input1 = listOf(0, 1)
      val expected1 = listOf(listOf(0, 1), listOf(1, 0))
      assertThat(Day07.generateAllUniqueSequences(input1)).isEqualTo(expected1)
      val input2 = listOf(0, 1, 2)
      val expected2 = listOf(
        listOf(0, 1, 2), listOf(0, 2, 1),
        listOf(1, 0, 2), listOf(1, 2, 0),
        listOf(2, 0, 1), listOf(2, 1, 0)
      )

      val generated = Day07.generateAllUniqueSequences(input2)

      assertThat(generated.size).isEqualTo(6)

      assertThat(generated).allSatisfy {
        it in expected2
      }
    }

    @Test
    fun `Expected output is generated from amp cycle`() {
      val example1 = intArrayOf(3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0)
      assertThat(Day07.getOutputForSettings(example1, listOf(4, 3, 2, 1, 0))).isEqualTo(43210)
    }

    @Test
    fun `Full run of examples generates expected output`() {
      val example1 = intArrayOf(3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0)
      assertThat(Day07.findBestPossibleConfig(example1)).isEqualTo(43210)
      val example2 = intArrayOf(
        3, 23, 3, 24, 1002, 24, 10, 24, 1002, 23, -1, 23,
        101, 5, 23, 23, 1, 24, 23, 23, 4, 23, 99, 0, 0
      )
      assertThat(Day07.findBestPossibleConfig(example2)).isEqualTo(54321)
      val example3 = intArrayOf(
        3, 31, 3, 32, 1002, 32, 10, 32, 1001, 31, -2, 31, 1007, 31, 0, 33,
        1002, 33, 7, 33, 1, 33, 31, 31, 1, 32, 31, 31, 4, 31, 99, 0, 0, 0
      )
      assertThat(Day07.findBestPossibleConfig(example3)).isEqualTo(65210)
    }

    @Test
    fun `Running input generates acceptable output`() {
      assertThat(Day07.findBestPossibleConfig(input)).isEqualTo(99376)
    }
  }
}