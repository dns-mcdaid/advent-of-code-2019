package com.dennismcdaid.advent2019

import com.dennismcdaid.advent2019.intcode.IntcodeComputer

object Day05 {
  fun execute(intArray: IntArray, input: Int): Int =
    IntcodeComputer(intArray)
      .apply { addInput(input) }
      .runThen {
        it.output.last()
      }
}