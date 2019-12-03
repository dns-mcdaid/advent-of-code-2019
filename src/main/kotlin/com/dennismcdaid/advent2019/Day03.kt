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
  fun collect(oldX: Int, newX: Int, oldY: Int, newY: Int)
}

private class Matrix : MutableMap<Int, SortedSet<Int>> by mutableMapOf(), Collector {
  override fun collect(oldX: Int, newX: Int, oldY: Int, newY: Int) {

    val minX = oldX.coerceAtMost(newX)
    val maxX = oldX.coerceAtLeast(newX)
    val minY = oldY.coerceAtMost(newY)
    val maxY = oldY.coerceAtLeast(newY)

    for (x in minX..maxX) {
      this[x]?.addAll(minY..maxY) ?: run {
        this[x] = (minY..maxY).toSortedSet()
      }
    }
  }
}

private class Sweeper(
  private val initialMatrix: Map<Int, SortedSet<Int>>
) : MutableList<Pair<Int, Int>> by mutableListOf(), Collector {
  override fun collect(oldX: Int, newX: Int, oldY: Int, newY: Int) {

    val minX = oldX.coerceAtMost(newX)
    val maxX = oldX.coerceAtLeast(newX)
    val minY = oldY.coerceAtMost(newY)
    val maxY = oldY.coerceAtLeast(newY)

    (minX..maxX).asSequence()
      .mapNotNull { x ->
        initialMatrix[x]?.let {
          Pair(x, it)
        }
      }
      .forEach { (x, ys) ->
        (minY..maxY).asSequence()
          .filter { it in ys }
          .forEach {
            add(x to it)
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

  fun shortestManhattan(intersections: List<Pair<Int, Int>>): Int {
    return intersections
      .map { it.first.absoluteValue + it.second.absoluteValue }
      .filter { it != 0 }
      .min() ?: throw IllegalStateException("List must be populated")
  }

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
          collector.collect(x, it.first, y, it.second)
        }
      }
      .let { collector }
  }

  private fun buildInitialMatrix(commands: List<String>): Map<Int, SortedSet<Int>> =
    traverse(commands, Matrix())

  private fun findIntersections(
    commands: List<String>,
    initialMatrix: Map<Int, SortedSet<Int>>
  ): List<Pair<Int, Int>> =
    traverse(commands, Sweeper(initialMatrix))

  fun execute(data: List<String>): Int {
    val (first, second) = massageData(data)
    val initial = buildInitialMatrix(first)
    val intersections = findIntersections(second, initial)
    return shortestManhattan(intersections)
  }
}