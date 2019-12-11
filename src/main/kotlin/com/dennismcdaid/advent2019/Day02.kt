package com.dennismcdaid.advent2019

import com.dennismcdaid.advent2019.intcode.IntcodeComputer

object Day02 {

  fun manipulateData(array: LongArray) : LongArray {
    return array.apply {
      this[1] = 12
      this[2] = 2
    }
  }

  fun firstRegisterAfterMutation(array: LongArray) : Long =
    IntcodeComputer(array).runThenGet { it[0] }

  fun getNounVerbMutated(source: LongArray, searchValue: Long) : Long {
    val (noun, verb) = determineNounAndVerb(source, searchValue)
    return 100 * noun + verb
  }

  private fun determineNounAndVerb(source: LongArray, searchValue: Long) : Pair<Long, Long> {
    for (noun in 0..99L) {
      for (verb in 0..99L) {
        val arrayForRun = LongArray(source.size)
        source.copyInto(arrayForRun)
        arrayForRun[1] = noun
        arrayForRun[2] = verb
        if (firstRegisterAfterMutation(arrayForRun) == searchValue) {
          return Pair(noun, verb)
        }
      }
    }
    throw IllegalStateException("Couldn't find any matches for $searchValue")
  }
}