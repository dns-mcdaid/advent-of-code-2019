package com.dennismcdaid.advent2019

import java.util.*
import kotlin.math.absoluteValue

enum class Direction(val rawValue: Char) {
  UP('U'),
  DOWN('D'),
  LEFT('L'),
  RIGHT('R');

  companion object {
    fun from(char: Char): Direction {
      return values().first { it.rawValue == char }
    }
  }
}

interface Collector {
  var stepCounter: Int
  fun collect(
    xRange: IntProgression,
    yRange: IntProgression
  )
}

private data class Intersection(
  val point: Point,
  val step: Int = 0
) : Comparable<Intersection> {

  val x = point.x
  val y = point.y

  fun jump(direction: Direction, steps: Int): Intersection {
    val newPoint = when (direction) {
      Direction.UP -> point.upBy(steps)
      Direction.DOWN -> point.downBy(steps)
      Direction.LEFT -> point.leftBy(steps)
      Direction.RIGHT -> point.rightBy(steps)
    }

    return Intersection(newPoint, step + steps)
  }

  override fun compareTo(other: Intersection): Int {
    return y.compareTo(other.y)
  }
}

private class Matrix : MutableMap<Int, SortedSet<Intersection>> by mutableMapOf(), Collector {
  override var stepCounter: Int = 1
  override fun collect(
    xRange: IntProgression,
    yRange: IntProgression
  ) {
    stepCounter--
    for (x in xRange) {
      this[x]?.addAll(yRange.map {
        Intersection(Point(x, it), stepCounter++)
      }) ?: run {
        this[x] = (yRange).map {
          Intersection(Point(x, it), stepCounter++)
        }.toSortedSet()
      }
    }
  }
}

private class Sweeper(
  private val initialMatrix: Map<Int, SortedSet<Intersection>>
) : MutableList<Intersection> by mutableListOf(), Collector {

  override var stepCounter: Int = 1
  override fun collect(
    xRange: IntProgression,
    yRange: IntProgression
  ) {
    stepCounter--
    for (x in xRange) {
      for (y in yRange) {
        initialMatrix[x]?.filter {
          it.y == y
        }?.forEach {
          add(Intersection(Point(x, y), it.step + stepCounter))
        }
        stepCounter++
      }
    }
  }
}

object Day03 {

  fun massageData(input: List<String>): List<List<String>> =
    input.map { it.split(",") }

  fun parseCommand(command: String): Pair<Direction, Int> {
    val direction = Direction.from(command[0])
    val steps = command.substring(1).toInt()
    return Pair(direction, steps)
  }

  private fun toProgression(old: Int, new: Int): IntProgression {
    return when {
      old > new -> old downTo new
      else -> old..new
    }
  }

  private fun <T : Collector> traverse(commands: List<String>, collector: T): T {
    return commands.map(this::parseCommand)
      .fold(
        Intersection(Point(0, 0), 0)
      ) { previous,
          (direction, steps) ->
        return@fold previous.jump(direction, steps).also {
          collector.collect(toProgression(previous.x, it.x), toProgression(previous.y, it.y))
        }
      }
      .let { collector }
  }

  private fun buildInitialMatrix(commands: List<String>): Map<Int, SortedSet<Intersection>> =
    traverse(commands, Matrix())

  private fun findIntersections(
    commands: List<String>,
    initialMatrix: Map<Int, SortedSet<Intersection>>
  ): List<Intersection> =
    traverse(commands, Sweeper(initialMatrix))

  fun execute01(data: List<String>): Int = execute(data) {
    it.point.distanceTo(Point.CENTER)
  }

  fun execute02(data: List<String>): Int = execute(data, Intersection::step)

  private fun execute(data: List<String>, mapFunction: (Intersection) -> Int): Int {
    val (first, second) = massageData(data)
    val initial = buildInitialMatrix(first)
    val intersections = findIntersections(second, initial)
    return intersections
      .map(mapFunction)
      .filter { it != 0 }
      .min()
      ?: throw IllegalStateException("No intersections found!")
  }
}