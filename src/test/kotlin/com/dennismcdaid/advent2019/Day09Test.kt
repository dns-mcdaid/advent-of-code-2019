package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 09")
class Day09Test {

  val input = Parser.readLongs("day09_input.txt")

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    @Test
    fun `Machine can handle larger numbers`() {
      val input1 = longArrayOf(104, 1125899906842624, 99)
      assertThat(Day09.boost(input1).first()).isEqualTo(1125899906842624)
      val input2 = longArrayOf(1102, 34915192, 34915192, 7, 4, 7, 99, 0)
      assertThat(Day09.boost(input2).first().toString().length).isEqualTo(16)
      val input3 = longArrayOf(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99)
      assertThat(Day09.boost(input3)).containsExactlyElementsOf(input3.toList())
    }

    @Test
    fun `Reddit examples output acceptable answers`() {
      val input1 = longArrayOf(109, -1, 4, 1, 99)
      assertThat(Day09.boost(input1).first()).isEqualTo(-1)
      val input2 = longArrayOf(109, -1, 104, 1, 99)
      assertThat(Day09.boost(input2).first()).isEqualTo(1)
      val input3 = longArrayOf(109, -1, 204, 1, 99)
      assertThat(Day09.boost(input3).first()).isEqualTo(109)
      val input4 = longArrayOf(109, 1, 9, 2, 204, -6, 99)
      assertThat(Day09.boost(input4).first()).isEqualTo(204)
      val input5 = longArrayOf(109, 1, 109, 9, 204, -6, 99)
      assertThat(Day09.boost(input5).first()).isEqualTo(204)
      val input6 = longArrayOf(109, 1, 209, -1, 204, -106, 99)
      assertThat(Day09.boost(input6).first()).isEqualTo(204)
      val input7 = longArrayOf(109, 1, 3, 3, 204, 2, 99)
      assertThat(Day09.boost(input7, 1)).isEqualTo(1)
      val input8 = longArrayOf(109, 1, 203, 2, 204, 2, 99)
      assertThat(Day09.boost(input8, 1)).isEqualTo(1)
    }

    @Test
    fun `Part 1 outputs acceptable answer`() {
      assertThat(Day09.boost(input, 1)).isEqualTo(3497884671)
    }
  }

  @Test
  fun `Part 2 outputs acceptable answer`() {
    println(Day09.boost(input, 2))
  }
}