package com.dennismcdaid.advent2019

import kotlin.math.absoluteValue

data class Point(
  val x: Int,
  val y: Int
) {

  fun upBy(by: Int = 1) = Point(x, y + by)
  fun downBy(by: Int = 1) = Point(x, y - by)
  fun leftBy(by: Int = 1) = Point(x - by, y)
  fun rightBy(by: Int = 1) = Point(x + by, y)

  fun distanceTo(other: Point) : Int {
    val xDist = (x - other.x).absoluteValue
    val yDist = (y - other.y).absoluteValue

    return xDist + yDist
  }

  companion object {
    val CENTER = Point(0, 0)
  }
}