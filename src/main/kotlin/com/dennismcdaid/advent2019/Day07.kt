package com.dennismcdaid.advent2019

object Day07 {

  private fun generateWithInput(command: Int, inputs: Iterator<Int>) : Day05.Instruction {
    return when (command % 100) {
      3 -> Day05.generateInstruction(command, inputs.next())
      else -> Day05.generateInstruction(command)
    }
  }

  fun generateAllUniqueSequences(numStack: List<Int>) : List<List<Int>> {
    if (numStack.size == 1) return listOf(numStack)
    return numStack.flatMap { num ->
      generateAllUniqueSequences(
        numStack.filter { it != num }
      ).map { seq ->
        listOf(num) + seq
      }
    }
  }

  fun runOnAmplifier(intArray: IntArray, inputs: List<Int>) : Int {
    val outputs = mutableListOf<Int>()
    val iterableInput = inputs.iterator()
    var currentIndex = 0
    do {
      currentIndex = generateWithInput(intArray[currentIndex], iterableInput)
        .also {
          (it as? Day05.Instruction.Output)?.getOutput(intArray, currentIndex)?.let(outputs::add)
        }
        .execute(intArray, currentIndex)
    } while (intArray[currentIndex] != 99)

    return outputs.last()
  }

  fun getOutputForSettings(intArray: IntArray, settings: List<Int>) : Int {
    return settings.fold(0, { total, phaseSetting ->
      runOnAmplifier(intArray.copyOf(), listOf(phaseSetting, total))
    })
  }

  fun findBestPossibleConfig(intArray: IntArray) : Int {
    return generateAllUniqueSequences(listOf(0, 1, 2, 3, 4))
      .map { getOutputForSettings(intArray, it) }
      .max() ?: throw IllegalStateException("There was no max!")
  }
}