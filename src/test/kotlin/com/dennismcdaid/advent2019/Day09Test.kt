package com.dennismcdaid.advent2019

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 09")
class Day09Test {

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    @Test
    fun `Machine can handle larger numbers`() {
      println(Long.MAX_VALUE)
      println(1125899906842624)
    }
  }
}