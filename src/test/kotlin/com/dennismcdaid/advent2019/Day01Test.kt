package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 01")
class Day01Test {

  val input = Parser.readLines("day01_input.txt")

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    @Test
    fun `Examples match`() {
      assertThat(Day01.mapMassToFuel(12)).isEqualTo(2)
      assertThat(Day01.mapMassToFuel(14)).isEqualTo(2)
      assertThat(Day01.mapMassToFuel(1969)).isEqualTo(654)
      assertThat(Day01.mapMassToFuel(100756)).isEqualTo(33583)
    }

    @Test
    fun `Acceptable output is generated`() {
      val converted = input.map { it.toInt() }
      assertThat(Day01.reduceRawMassToFuel(converted)).isEqualTo(3295206)
    }
  }

  @Nested
  @DisplayName("Part 2")
  inner class Part2 {

    @Test
    fun `Examples match`() {
      assertThat(Day01.reduceIndividualMassToFuel(12)).isEqualTo(2)
      assertThat(Day01.reduceIndividualMassToFuel(14)).isEqualTo(2)
      assertThat(Day01.reduceIndividualMassToFuel(1969)).isEqualTo(966)
      assertThat(Day01.reduceIndividualMassToFuel(100756)).isEqualTo(50346)
    }

    @Test
    fun `Acceptable output is generated`() {
      val converted = input.map { it.toInt() }
      assertThat(Day01.reduceTotalMassToFuel(converted)).isEqualTo(4939939)
    }
  }
}