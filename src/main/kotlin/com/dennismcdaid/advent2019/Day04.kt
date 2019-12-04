package com.dennismcdaid.advent2019

import kotlin.math.pow

object Day04 {

  /**
   * A number is valid if:
   *
   * It is a six-digit number.
   * The value is within the range given in your puzzle input.
   * Two adjacent digits are the same (like 22 in 122345).
   * Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
   */
  private fun matches(digits: List<Int>) : Boolean {
    val validSize = digits.size == 6
    val isSorted = digits.sorted() == digits
    val containsADuplicate = digits.toSet().size < digits.size
    return validSize && isSorted && containsADuplicate
  }

  private fun narrowerMatch(digits: List<Int>) : Boolean {
    return matches(digits) && hasAtLeastOneGroupOfTwo(digits)
  }

  fun hasAtLeastOneGroupOfTwo(digits: List<Int>) : Boolean = digits.mapIndexed { index, i ->
    val previous = digits.getOrNull(index - 1)
    val next = digits.getOrNull(index + 1)
    val nextNext = digits.getOrNull(index + 2)
    return@mapIndexed i == next && i != nextNext && i != previous
  }
    .any { it }

  fun matches(number: Int) : Boolean = matches(toDigits(number))

  fun toDigits(
    number: Int,
    existing: MutableList<Int> = mutableListOf()
  ) : List<Int> {
    existing.add(number % 10)
    return if (number / 10 == 0) existing.reversed()
    else toDigits(number / 10, existing)
  }

  fun toNumber(digits: List<Int>) = digits.reversed().foldIndexed(0.0, { index, acc, i ->
    acc + i * 10.0.pow(index.toDouble())
  }).toInt()

  // TODO: This can be heavily optimized.
  //  Whole ranges of numbers can be skipped by evaluating the result of toDigits
  //  and upping ints which are smaller than their left neighbors.
  private fun findMatchesInRange(start: Int, end: Int, searchFilter: (List<Int>) -> Boolean) : List<Int> {
    return (start..end).toList()
      .filter {
        searchFilter(toDigits(it))
      }
  }

  fun getPossiblePasswordCount(start: Int, end: Int) : Int =
    findMatchesInRange(start, end, this::matches).size

  fun getNarrowerPasswordCount(start: Int, end: Int) : Int =
    findMatchesInRange(start, end, this::narrowerMatch).size
}