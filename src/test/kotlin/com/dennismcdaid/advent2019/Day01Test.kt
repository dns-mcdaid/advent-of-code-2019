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
      assertThat(Day01.calculateFuelForMass(12)).isEqualTo(2)
      assertThat(Day01.calculateFuelForMass(14)).isEqualTo(2)
      assertThat(Day01.calculateFuelForMass(1969)).isEqualTo(654)
      assertThat(Day01.calculateFuelForMass(100756)).isEqualTo(33583)
    }

    @Test
    fun `Acceptable output is generated`() {
      val converted = input.map { it.toInt() }
      assertThat(Day01.calculateTotalFuel(converted)).isEqualTo(3295206)
    }
  }
}