package com.dennismcdaid.advent2019

import com.dennismcdaid.advent2019.intcode.IntcodeComputer
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
      val computer1 = IntcodeComputer(day05input.copyOf()).apply { addInput(1) }
      val computer2 = IntcodeComputer(day05input.copyOf()).apply { addInput(5) }
      assertThat(Day07.runOnAmplifier(computer1)).isEqualTo(12896948)
      assertThat(Day07.runOnAmplifier(computer2)).isEqualTo(7704130)
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
      assertThat(Day07.findBestPossibleConfig(example1, listOf(0, 1, 2, 3, 4))).isEqualTo(43210)
      val example2 = intArrayOf(
        3, 23, 3, 24, 1002, 24, 10, 24, 1002, 23, -1, 23,
        101, 5, 23, 23, 1, 24, 23, 23, 4, 23, 99, 0, 0
      )
      assertThat(Day07.findBestPossibleConfig(example2, listOf(0, 1, 2, 3, 4))).isEqualTo(54321)
      val example3 = intArrayOf(
        3, 31, 3, 32, 1002, 32, 10, 32, 1001, 31, -2, 31, 1007, 31, 0, 33,
        1002, 33, 7, 33, 1, 33, 31, 31, 1, 32, 31, 31, 4, 31, 99, 0, 0, 0
      )
      assertThat(Day07.findBestPossibleConfig(example3, listOf(0, 1, 2, 3, 4))).isEqualTo(65210)
    }

    @Test
    fun `Input generates acceptable output`() {
      assertThat(Day07.findBestPossibleConfig(input, listOf(0, 1, 2, 3, 4))).isEqualTo(99376)
    }
  }

  @Nested
  @DisplayName("Part 2")
  inner class Part2 {
    @Test
    fun `Examples generate expected results`() {
      val memory1 = intArrayOf(
        3, 26, 1001, 26, -4, 26, 3, 27, 1002, 27, 2, 27, 1, 27, 26,
        27, 4, 27, 1001, 28, -1, 28, 1005, 28, 6, 99, 0, 0, 5
      )
      val settings1 = listOf(9, 8, 7, 6, 5)
      val output1 = 139629729
      assertThat(Day07.getFeedbackForSequence(memory1, settings1)).isEqualTo(output1)
      val memory2 = intArrayOf(
        3, 52, 1001, 52, -5, 52, 3, 53, 1, 52, 56, 54, 1007, 54, 5, 55, 1005, 55, 26, 1001, 54,
        -5, 54, 1105, 1, 12, 1, 53, 54, 53, 1008, 54, 0, 55, 1001, 55, 1, 55, 2, 53, 55, 53, 4,
        53, 1001, 56, -1, 56, 1005, 56, 6, 99, 0, 0, 0, 0, 10
      )
      val settings2 = listOf(9, 7, 8, 5, 6)
      val output2 = 18216
      assertThat(Day07.getFeedbackForSequence(memory2, settings2)).isEqualTo(output2)
    }

    @Test
    fun `Input generates acceptable output`() {
      assertThat(Day07.findBestPossibleFeedback(input, (5..9).toList())).isEqualTo(8754464)
    }
  }
}