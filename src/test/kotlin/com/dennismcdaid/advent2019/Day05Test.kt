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
      val expectedOutput = intArrayOf(1002, 4, 3, 4, 99)
      val index = 0
      val instruction = Day05.generateInstruction(example[index])
      assertThat(instruction).isInstanceOf(Day05.Instruction.Multiplication::class.java)
      assertThat(instruction.modes).isEqualTo(listOf(0, 1, 0))
      instruction.execute(example, index)
      assertThat(example).isEqualTo(expectedOutput)
    }

    @Test
    fun `Part 1 generates expected result`() {
      assertThat(Day05.execute(input.copyOf(), 1)).isEqualTo(12896948)
    }
  }

  @Nested
  @DisplayName("Part 2")
  inner class Part2 {

    @Test
    fun `New instructions generate correctly`() {
      assertThat(Day05.generateInstruction(5)).isInstanceOf(Day05.Instruction.Jump::class.java)
      assertThat(Day05.generateInstruction(6)).isInstanceOf(Day05.Instruction.Jump::class.java)
      assertThat((Day05.generateInstruction(5) as Day05.Instruction.Jump).condition).isEqualTo(true)
      assertThat((Day05.generateInstruction(6) as Day05.Instruction.Jump).condition).isEqualTo(false)
    }

    @Test
    fun `Jump if true instruction handles as expected`() {
      val jIfTrueList = intArrayOf(1105, 2, 47)
      val falseList = intArrayOf(1105, 0, 13)
      val instruction = Day05.generateInstruction(jIfTrueList[0])
      assertThat(instruction.execute(jIfTrueList, 0)).isEqualTo(47)
      assertThat(instruction.execute(falseList, 0)).isEqualTo(instruction.size)
    }

    @Test
    fun `Jump if false instruction handles as expected`() {
      val nonzero = intArrayOf(1106, 2, 47)
      val zero = intArrayOf(1106, 0, 13)
      val instruction = Day05.generateInstruction(nonzero[0])
      assertThat(instruction.execute(nonzero, 0)).isEqualTo(instruction.size)
      assertThat(instruction.execute(zero, 0)).isEqualTo(13)
    }

    @Test
    fun `Basic examples pass`() {
      val example0 = intArrayOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
      assertThat(Day05.execute(example0, 8)).isEqualTo(1)
      val example1 = intArrayOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
      assertThat(Day05.execute(example1, 8)).isEqualTo(0)
      val example2 = intArrayOf(3, 3, 1108, -1, 8, 3, 4, 3, 99)
      assertThat(Day05.execute(example2, 8)).isEqualTo(1)
      val example3 = intArrayOf(3, 3, 1107, -1, 8, 3, 4, 3, 99)
      assertThat(Day05.execute(example3, 8)).isEqualTo(0)
      val example4 = intArrayOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
      assertThat(Day05.execute(example4.copyOf(), 0)).isEqualTo(0)
      assertThat(Day05.execute(example4.copyOf(), 1)).isEqualTo(1)
      val example5 = intArrayOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
      assertThat(Day05.execute(example5.copyOf(), 0)).isEqualTo(0)
      assertThat(Day05.execute(example5.copyOf(), 1)).isEqualTo(1)
    }

    @Test
    fun `Larger example generates expected results`() {
      val largerExample = intArrayOf(
        3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
        1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
        999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
      )

      assertThat(Day05.execute(largerExample.copyOf(), 7)).isEqualTo(999)
      assertThat(Day05.execute(largerExample.copyOf(), 8)).isEqualTo(1000)
      assertThat(Day05.execute(largerExample.copyOf(), 9)).isEqualTo(1001)
    }

    @Test
    fun `Part 2 generates expected output`() {
      assertThat(Day05.execute(input.copyOf(), 5)).isEqualTo(7704130)
    }
  }
}