package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 03")
class Day03Test {

  val example0 = listOf(
    "R8,U5,L5,D3",
    "U7,R6,D4,L4"
  )

  val example1 = listOf(
    "R75,D30,R83,U83,L12,D49,R71,U7,L72",
    "U62,R66,U55,R34,D71,R55,D58,R83"
  )

  val example2 = listOf(
    "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51",
    "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
  )

  val input = Parser.readLines("day03_input.txt")

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    @Test
    fun `Data is broken down as expected`() {
      val massaged = Day03.massageData(example1)

      assertThat(massaged.size).isEqualTo(2)
      assertThat(massaged[0].size).isEqualTo(9)
      assertThat(massaged[1].size).isEqualTo(8)
    }

    @Test
    fun `Strings map to directions`() {
      val expected = listOf(
        Direction.RIGHT, Direction.DOWN, Direction.RIGHT, Direction.UP,
        Direction.LEFT, Direction.DOWN, Direction.RIGHT, Direction.UP, Direction.LEFT
      )

      val firstLineDirections = Day03.massageData(example1)[0].map { Direction.from(it[0]) }

      assertThat(firstLineDirections).isEqualTo(expected)
    }

    @Test
    fun `Command is parsed with steps`() {
      val example = "U35"
      val expected = Pair(Direction.UP, 35)
      assertThat(Day03.parseCommand(example)).isEqualTo(expected)
    }

    @Test
    fun `Shortest Manhattan is determined from list of intersections`() {
      val crossovers = listOf(
        6 to 6,
        3 to 3
      )

      assertThat(Day03.shortestManhattan(crossovers)).isEqualTo(6)
    }

    @Test
    fun `Examples pass`() {
      assertThat(Day03.execute(example0)).isEqualTo(6)
      assertThat(Day03.execute(example1)).isEqualTo(159)
      assertThat(Day03.execute(example2)).isEqualTo(135)
    }

    @Test
    fun `Input generates expected result`() {
      assertThat(Day03.execute(input)).isEqualTo(293)
    }
  }
}