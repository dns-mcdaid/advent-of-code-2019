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
      assertThat(Day05.generateInstruction(3)).isInstanceOf(Day05.Instruction.Save::class.java)
      assertThat(Day05.generateInstruction(4)).isInstanceOf(Day05.Instruction.Output::class.java)
      assertThat(Day05.generateInstruction(1)).isInstanceOf(Day05.Instruction.Addition::class.java)
      assertThat(Day05.generateInstruction(1002)).isInstanceOf(Day05.Instruction.Multiplication::class.java)
      assertThat(Day05.generateInstruction(99)).isInstanceOf(Day05.Instruction.Complete::class.java)
      assertThat(Day05.generateInstruction(1002).modes).isEqualTo(listOf(0, 1, 0))
      assertThat(Day05.generateInstruction(11101).modes).isEqualTo(listOf(1, 1, 1))
    }

    @Test
    fun `Operation performs correctly`() {
      val example = intArrayOf(1002, 4, 3, 4, 33)
      val expectedOutput = intArrayOf(1002, 4, 3, 99, 33)
      val index = 0
      val instruction = Day05.generateInstruction(example[index])
      assertThat(instruction).isInstanceOf(Day05.Instruction.Multiplication::class.java)
      assertThat(instruction.modes).isEqualTo(listOf(0, 1, 0))
      instruction.execute(example, index)
      assertThat(example).isEqualTo(expectedOutput)
    }

    @Test
    fun `Part 1 executes`() {
      assertThat(Day05.execute(input, 1)).isEqualTo(12896948)
    }
  }
}