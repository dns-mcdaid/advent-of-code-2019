package com.dennismcdaid.advent2019

import kotlin.math.abs
import kotlin.math.atan2

data class Point(
  val x: Int,
  val y: Int
) {

  fun upBy(by: Int = 1) = Point(x, y + by)
  fun downBy(by: Int = 1) = Point(x, y - by)
  fun leftBy(by: Int = 1) = Point(x - by, y)
  fun rightBy(by: Int = 1) = Point(x + by, y)

  fun manhattanTo(other: Point) : Int {
    val xDist = abs(x - other.x)
    val yDist = abs(y - other.y)

    return xDist + yDist
  }

  fun angle(other: Point) : Double {
    val x = other.x - x
    val y = other.y - y
    return atan2(x.toDouble(), y.toDouble())
  }

  fun adjacent(direction: Direction) : Point {
    return when (direction) {
      Direction.NORTH -> upBy()
      Direction.EAST -> rightBy()
      Direction.SOUTH -> downBy()
      Direction.WEST -> leftBy()
    }
  }

  companion object {
    val CENTER = Point(0, 0)
  }
}

enum class Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST;
}