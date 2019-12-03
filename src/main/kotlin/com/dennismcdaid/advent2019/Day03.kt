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
  fun collect(
    xRange: IntRange,
    yRange: IntRange
  )
}

data class Intersection(
  val x: Int,
  val y: Int
)

private class Matrix : MutableMap<Int, SortedSet<Int>> by mutableMapOf(), Collector {
  override fun collect(
    xRange: IntRange,
    yRange: IntRange
  ) {

    for (x in xRange) {
      this[x]?.addAll(yRange) ?: run {
        this[x] = (yRange).toSortedSet()
      }
    }
  }
}

private class Sweeper(
  private val initialMatrix: Map<Int, SortedSet<Int>>
) : MutableList<Intersection> by mutableListOf(), Collector {
  override fun collect(
    xRange: IntRange,
    yRange: IntRange
  ) {

    xRange.mapNotNull { x ->
        initialMatrix[x]?.let {
          Pair(x, it)
        }
      }
      .forEach { (x, ys) ->
        ys.forEach { y ->
          if (y in yRange) add(Intersection(x, y))
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

  fun shortestManhattan(intersections: List<Intersection>): Int {
    return intersections
      .map { it.x.absoluteValue + it.y.absoluteValue }
      .filter { it != 0 }
      .min() ?: throw IllegalStateException("List must be populated")
  }

  private fun minMaxRange(old: Int, new: Int) : IntRange =
    old.coerceAtMost(new)..old.coerceAtLeast(new)

  private fun <T : Collector> traverse(commands: List<String>, collector: T): T {
    return commands.map(this::parseCommand)
      .fold(Pair(0, 0)) { (x, y),
                          (direction, steps) ->
        return@fold when (direction) {
          Direction.UP -> Pair(x, y + steps)
          Direction.DOWN -> Pair(x, y - steps)
          Direction.LEFT -> Pair(x - steps, y)
          Direction.RIGHT -> Pair(x + steps, y)
        }.also {
          collector.collect(minMaxRange(x, it.first), minMaxRange(y, it.second))
        }
      }
      .let { collector }
  }

  private fun buildInitialMatrix(commands: List<String>): Map<Int, SortedSet<Int>> =
    traverse(commands, Matrix())

  private fun findIntersections(
    commands: List<String>,
    initialMatrix: Map<Int, SortedSet<Int>>
  ): List<Intersection> =
    traverse(commands, Sweeper(initialMatrix))

  fun execute(data: List<String>): Int {
    val (first, second) = massageData(data)
    val initial = buildInitialMatrix(first)
    val intersections = findIntersections(second, initial)
    return shortestManhattan(intersections)
  }
}