package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 05")
class Day05Test {

  val example = intArrayOf(1002, 4, 3, 4, 33)
  val input = Parser.readInts("day05_input.txt")

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    @Test
    fun `Instruction generates correctly`() {
      assertThat(Day05.Instruction.from(1002)).isEqualTo(Day05.Instruction(2, listOf(0, 1, 0)))
    }
  }
}