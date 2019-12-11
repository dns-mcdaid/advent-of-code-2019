package com.dennismcdaid.advent2019

import com.dennismcdaid.advent2019.intcode.IntcodeComputer

object Day07 {

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

  fun runOnAmplifier(computer: IntcodeComputer) : Int = computer.runThenGet {
    it.output.last()
  }

  fun getOutputForSettings(intArray: IntArray, settings: List<Int>) : Int {
    return settings.fold(0, { total, phaseSetting ->
      val computer = IntcodeComputer(intArray.copyOf())
        .apply {
          addInput(phaseSetting)
          addInput(total)
        }
      return@fold runOnAmplifier(computer)
    })
  }

  fun findBestPossibleConfig(intArray: IntArray, phaseSettings: List<Int>) : Int {
    return generateAllUniqueSequences(phaseSettings)
      .map { getOutputForSettings(intArray, it) }
      .max() ?: throw IllegalStateException("There was no max!")
  }

  private fun setupAmps(memory: IntArray, phaseSettings: List<Int>) : Iterator<Pair<Int, IntcodeComputer>> {
    return phaseSettings.map {
      IntcodeComputer(memory.copyOf())
        .apply { addInput(it) }
    }
      .asSequence()
      .indexedCycleIterator()
  }

  fun getFeedbackForSequence(memory: IntArray, phaseSettings: List<Int>) : Int {
    val amps = setupAmps(memory, phaseSettings)
    var input = 0
    var ampNumber: Int
    var amp: IntcodeComputer
    do {
      val combo = amps.next()
      ampNumber = combo.first
      amp = combo.second
      amp.addInput(input)
      val originalOutputSize = amp.output.size
      amp.runUntil {
        it.output.size > originalOutputSize || it.current == 99
      }.let {
        input = it.output.last()
      }
    } while (amp.current != 99 || ampNumber != phaseSettings.size-1)
    return input
  }

  fun findBestPossibleFeedback(memory: IntArray, phaseSettings: List<Int>) : Int {
    return generateAllUniqueSequences(phaseSettings)
      .map { getFeedbackForSequence(memory, it) }
      .max() ?: throw IllegalStateException("There was no max!")
  }
}