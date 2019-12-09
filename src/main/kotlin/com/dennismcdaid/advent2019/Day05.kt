package com.dennismcdaid.advent2019

import com.dennismcdaid.advent2019.intcode.Instruction

object Day05 {

  fun execute(intArray: IntArray, input: Int): Int {
    val outputs = mutableListOf<Int>()
    var instruction: Instruction
    var index = 0
    do {
      instruction = Instruction.from(intArray[index], input)
      (instruction as? Instruction.Output)?.getOutput(intArray, index)?.let(outputs::add)
      index = instruction.execute(intArray, index)
    } while (instruction !is Instruction.Complete)
    return outputs.last()
  }

}