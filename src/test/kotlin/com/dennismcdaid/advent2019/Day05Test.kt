package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//@DisplayName("Day 05")
//class Day05Test {
//
//  val example = intArrayOf(1002, 4, 3, 4, 33)
//  val input = Parser.readInts("day05_input.txt")
//
//  @Nested
//  @DisplayName("Part 1")
//  inner class Part1 {
//
//    @Test
//    fun `Part 1 generates expected result`() {
//      assertThat(Day05.execute(input.copyOf(), 1)).isEqualTo(12896948)
//    }
//  }
//
//  @Nested
//  @DisplayName("Part 2")
//  inner class Part2 {
//
//    @Test
//    fun `Basic examples pass`() {
//      val example0 = intArrayOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
//      assertThat(Day05.execute(example0, 8)).isEqualTo(1)
//      val example1 = intArrayOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
//      assertThat(Day05.execute(example1, 8)).isEqualTo(0)
//      val example2 = intArrayOf(3, 3, 1108, -1, 8, 3, 4, 3, 99)
//      assertThat(Day05.execute(example2, 8)).isEqualTo(1)
//      val example3 = intArrayOf(3, 3, 1107, -1, 8, 3, 4, 3, 99)
//      assertThat(Day05.execute(example3, 8)).isEqualTo(0)
//      val example4 = intArrayOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
//      assertThat(Day05.execute(example4.copyOf(), 0)).isEqualTo(0)
//      assertThat(Day05.execute(example4.copyOf(), 1)).isEqualTo(1)
//      val example5 = intArrayOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
//      assertThat(Day05.execute(example5.copyOf(), 0)).isEqualTo(0)
//      assertThat(Day05.execute(example5.copyOf(), 1)).isEqualTo(1)
//    }
//
//    @Test
//    fun `Larger example generates expected results`() {
//      val largerExample = intArrayOf(
//        3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
//        1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
//        999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
//      )
//
//      assertThat(Day05.execute(largerExample.copyOf(), 7)).isEqualTo(999)
//      assertThat(Day05.execute(largerExample.copyOf(), 8)).isEqualTo(1000)
//      assertThat(Day05.execute(largerExample.copyOf(), 9)).isEqualTo(1001)
//    }
//
//    @Test
//    fun `Part 2 generates expected output`() {
//      assertThat(Day05.execute(input.copyOf(), 5)).isEqualTo(7704130)
//    }
//  }
//}