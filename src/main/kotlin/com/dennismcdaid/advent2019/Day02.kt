package com.dennismcdaid.advent2019

import com.dennismcdaid.advent2019.intcode.IntcodeComputer

object Day02 {

  fun manipulateData(array: IntArray) : IntArray {
    return array.apply {
      this[1] = 12
      this[2] = 2
    }
  }

  fun firstRegisterAfterMutation(array: IntArray) : Int =
    IntcodeComputer(array).runThenGet { it[0] }

  fun getNounVerbMutated(source: IntArray, searchValue: Int) : Int {
    val (noun, verb) = determineNounAndVerb(source, searchValue)
    return 100 * noun + verb
  }

  private fun determineNounAndVerb(source: IntArray, searchValue: Int) : Pair<Int, Int> {
    for (noun in 0..99) {
      for (verb in 0..99) {
        val arrayForRun = IntArray(source.size)
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