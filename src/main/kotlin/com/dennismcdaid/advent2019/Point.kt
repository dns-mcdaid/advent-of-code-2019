package com.dennismcdaid.advent2019

import kotlin.math.abs

data class Point(
  val x: Int,
  val y: Int
) {

  fun upBy(by: Int = 1) = Point(x, y + by)
  fun downBy(by: Int = 1) = Point(x, y - by)
  fun leftBy(by: Int = 1) = Point(x - by, y)
  fun rightBy(by: Int = 1) = Point(x + by, y)

  fun distanceTo(other: Point) : Int {
    val xDist = abs(x - other.x)
    val yDist = abs(y - other.y)

    return xDist + yDist
  }

  companion object {
    val CENTER = Point(0, 0)
  }
}