package com.dennismcdaid.advent2019

import com.dennismcdaid.advent2019.intcode.IntcodeComputer

object Day09 {

  fun boost(memory: LongArray) : List<Long> = IntcodeComputer(memory).runThenGet {
    it.output.toList()
  }

  fun boost(memory: LongArray, input: Long) :Long = IntcodeComputer(memory)
    .apply { addInput(input) }
    .runThenGet {
      it.output.last()
    }

}