package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 06")
class Day06Test {

  val input = Parser.readLines("day06_input.txt")
  val example = Parser.readLines("day06_example.txt")

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    @Test
    fun `Parsing generates expected string`() {
      val relationship = "A)B"
      assertThat(Day06.parseOrbit(relationship)).isEqualTo(listOf("A", "B"))
    }

    @Test
    fun `Basic mapping builds expected map`() {
      val relationship = "A)B"
      val expectedOutput = mapOf(
        "A" to Day06.Orbital("A", mutableListOf(Day06.Orbital("B"))),
        "B" to Day06.Orbital("B", mutableListOf())
      )

      assertThat(Day06.mapOrbits(listOf(relationship))).isEqualTo(expectedOutput)
    }

    @Test
    fun `Direct orbit count is found`() {
      val example0 = Day06.Orbital(Day06.CENTER, mutableListOf(Day06.Orbital("B")))
      assertThat(Day06.getOrbitsRecursively(example0)).isEqualTo(1)
      val example1 = Day06.Orbital(Day06.CENTER, mutableListOf(Day06.Orbital("B"), Day06.Orbital("C")))
      assertThat(Day06.getOrbitsRecursively(example1)).isEqualTo(2)
      val example2 = Day06.Orbital(Day06.CENTER,
        mutableListOf(Day06.Orbital("B"),
          Day06.Orbital("C", mutableListOf(
            Day06.Orbital("D")
          ))))
      assertThat(Day06.getOrbitsRecursively(example2)).isEqualTo(4)
    }

    @Test
    fun `Given example generates expected output`() {
      assertThat(Day06.getNumberOfOrbits(example)).isEqualTo(42)
    }

    @Test
    fun `Input example generates expected output`() {
      assertThat(Day06.getNumberOfOrbits(input)).isEqualTo(162439)
    }
  }

  @Nested
  @DisplayName("Part 2")
  inner class Part2 {
    private val modifiedExample = example + listOf("K)YOU", "I)SAN")

    @Test
    fun `Reverse mapping builds inverse map`() {
      val relationship = "A)B"
      val expectedOutput = mapOf(
        "B" to Day06.Orbital("B", mutableListOf(Day06.Orbital("A"))),
        "A" to Day06.Orbital("A", mutableListOf())
      )

      assertThat(Day06.mapOrbits(listOf(relationship), inverse = true)).isEqualTo(expectedOutput)
    }

    @Test
    fun `Updated example gets expected distance`() {
      assertThat(Day06.findDistanceToSanta(modifiedExample)).isEqualTo(4)
    }

    @Test
    fun `Input finds expected distance`() {
      assertThat(Day06.findDistanceToSanta(input)).isEqualTo(367)
    }
  }
}