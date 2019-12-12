package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 11")
class Day11Test {

  val input = Parser.readLongs("day11_input.txt")

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    @Test
    fun `Robot does the right thing`() {
      val commands = listOf(
        1L to 0L,
        0L to 0L,
        1L to 0L,
        1L to 0L,
        0L to 1L,
        1L to 0L,
        1L to 0L
      )

      val robot = Day11.Robot()

      commands.forEach { (_, direction) ->
        robot.turn(direction)
      }

      assertThat(robot.location).isEqualTo(Point(0, 1))
      assertThat(robot.direction).isEqualTo(Direction.WEST)
    }

    @Test
    fun `Part 1 generates acceptable answer`() {
      assertThat(Day11.collectColoredTiles(input).size).isEqualTo(2219)
    }
  }

  @Test
  fun `Part 2 prints acceptable result`() {
    val colorMap = Day11.collectColoredTiles(input, initial = 1)
    Day11.printOutput(colorMap)
  }
}