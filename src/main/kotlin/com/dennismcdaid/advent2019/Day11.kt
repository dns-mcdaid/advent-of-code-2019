package com.dennismcdaid.advent2019

import com.dennismcdaid.advent2019.intcode.IntcodeComputer

object Day11 {

  class Robot {
    var direction = Direction.NORTH
    var location = Point(0, 0)

    private fun turnRight() {
      direction = Direction.values().firstOrNull {
        it.ordinal == direction.ordinal + 1
      } ?: Direction.NORTH
    }

    private fun turnLeft() {
      direction = Direction.values().firstOrNull {
        it.ordinal == direction.ordinal - 1
      } ?: Direction.WEST
    }

    fun turn(direction: Long) {
      when (direction) {
        0L -> turnLeft()
        1L -> turnRight()
      }
      location = location.adjacent(this.direction)
    }
  }

  fun collectColoredTiles(memory: LongArray, initial: Long = 0): Map<Point, Long> {
    val colorMap = mutableMapOf<Point, Long>()
    val robot = Robot()
    val machine = IntcodeComputer(memory)
      .apply { addInput(initial) }
    var cycle = 0
    while (machine.current != 99L) {
      cycle++
      machine.runUntil {
        it.output.size == 2 || machine.current == 99L
      }
        .takeIf { it.output.size == 2 }
        ?.let {
          val (color, direction) = it.output
          it.output.clear()
          colorMap[robot.location] = color
          robot.turn(direction)
          machine.addInput(colorMap[robot.location] ?: 0)
        }
    }
    return colorMap
  }

  fun printOutput(colors: Map<Point, Long>) {
    val xs = colors.keys.map { it.x }
    val ys = colors.keys.map { it.y }

    val output = (ys.min()!!..ys.max()!!).map { y ->
      (xs.min()!!..xs.max()!!).map { x ->
        charFor(colors[Point(x, y)] ?: 0)
      }.toCharArray()
    }

    for (line in output.reversed()) {
      println(String(line))
    }
  }

  private fun charFor(color: Long) = when (color) {
    1L -> '#'
    else -> ' '
  }
}