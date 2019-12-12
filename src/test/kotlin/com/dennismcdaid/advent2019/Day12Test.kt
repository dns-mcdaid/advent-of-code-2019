package com.dennismcdaid.advent2019

import javafx.geometry.Pos
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 12")
class Day12Test {

  val example1 = listOf(
    Position(-1, 0, 2),
    Position(2, -10, -7),
    Position(4, -8, 8),
    Position(3, 5, -1)
  )

  val example2 = listOf(
    Position(x = -8, y = -10, z = 0),
    Position(x = 5, y = 5, z = 10),
    Position(x = 2, y = -7, z = 3),
    Position(x = 9, y = -8, z = -3)
  )

  val input = Parser.readLines("day12_input.txt")

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    @Test
    fun `Single cycle outputs expected results`() {
      val exampleMoons = example1.map {
        Moon(it, Velocity.STARTING)
      }
      val expectedMoons = listOf(
        Moon(
          Position(x = 2, y = -1, z = 1), Velocity(x = 3, y = -1, z = -1)
        ),
        Moon(
          Position(x = 3, y = -7, z = -4), Velocity(x = 1, y = 3, z = 3)
        ),
        Moon(
          Position(x = 1, y = -7, z = 5), Velocity(x = -3, y = 1, z = -3)
        ),
        Moon(
          Position(x = 2, y = 2, z = 0), Velocity(x = -1, y = -3, z = 1)
        )
      )

      assertThat(Day12.simulateMotion(exampleMoons))
        .containsExactlyElementsOf(expectedMoons)
    }

    @Test
    fun `10 cycles outputs expected results`() {
      val exampleMoons = example1.map {
        Moon(it, Velocity.STARTING)
      }
      val expectedMoons = listOf(
        Moon(
          Position(x = 2, y = 1, z = -3), Velocity(x = -3, y = -2, z = 1)
        ),
        Moon(
          Position(x = 1, y = -8, z = 0), Velocity(x = -1, y = 1, z = 3)
        ),
        Moon(
          Position(x = 3, y = -6, z = 1), Velocity(x = 3, y = 2, z = -3)
        ),
        Moon(
          Position(x = 2, y = 0, z = 4), Velocity(x = 1, y = -1, z = -1)
        )
      )

      assertThat(Day12.simulateMotionRepeatedly(exampleMoons, 10))
        .containsExactlyElementsOf(expectedMoons)
    }

    @Test
    fun `Total energy is calculated correctly`() {
      val exampleMoons = listOf(
        Moon(
          Position(x = 2, y = 1, z = -3), Velocity(x = -3, y = -2, z = 1)
        ),
        Moon(
          Position(x = 1, y = -8, z = 0), Velocity(x = -1, y = 1, z = 3)
        ),
        Moon(
          Position(x = 3, y = -6, z = 1), Velocity(x = 3, y = 2, z = -3)
        ),
        Moon(
          Position(x = 2, y = 0, z = 4), Velocity(x = 1, y = -1, z = -1)
        )
      )

      assertThat(Day12.getTotalEnergy(exampleMoons)).isEqualTo(179)
    }

    @Test
    fun `Total energy is calculated as expected after simulating motion`() {
      val exampleMoons1 = example1.map {
        Moon(it, Velocity.STARTING)
      }

      assertThat(Day12.getEnergyAfterMotions(exampleMoons1, 10)).isEqualTo(179)

      val exampleMoons2 = example2.map {
        Moon(it, Velocity.STARTING)
      }

      assertThat(Day12.getEnergyAfterMotions(exampleMoons2, 100)).isEqualTo(1940)
    }

    @Test
    fun `Input converts to moon`() {
      val expectedOutput = listOf(
        Moon(
          Position(x = -10, y = -13, z = 7),
          Velocity.STARTING
        ),
        Moon(
          Position(x = 1, y = 2, z = 1),
          Velocity.STARTING
        ),
        Moon(
          Position(x = -15, y = -3, z = 13),
          Velocity.STARTING
        ),
        Moon(
          Position(x = 3, y = 7, z = -4),
          Velocity.STARTING
        )
      )
      assertThat(Day12.moonsFromInput(input)).containsExactlyElementsOf(expectedOutput)
    }

    @Test
    fun `Input generates acceptable output`() {
      val moons = Day12.moonsFromInput(input)
      assertThat(Day12.getEnergyAfterMotions(moons, 1000)).isEqualTo(8454)
    }
  }
}