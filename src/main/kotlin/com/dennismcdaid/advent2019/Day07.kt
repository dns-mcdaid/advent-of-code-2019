package com.dennismcdaid.advent2019

import com.dennismcdaid.advent2019.intcode.IntcodeComputer
import com.dennismcdaid.advent2019.intcode.Operation

object Day07 {

  fun generateAllUniqueSequences(numStack: List<Long>) : List<List<Long>> {
    if (numStack.size == 1) return listOf(numStack)
    return numStack.flatMap { num ->
      generateAllUniqueSequences(
        numStack.filter { it != num }
      ).map { seq ->
        listOf(num) + seq
      }
    }
  }

  fun runOnAmplifier(computer: IntcodeComputer) : Long = computer.runThenGet {
    it.output.last()
  }

  fun getOutputForSettings(intArray: LongArray, settings: List<Long>) : Long {
    return settings.fold(0L, { total, phaseSetting ->
      val computer = IntcodeComputer(intArray.copyOf())
        .apply {
          addInput(phaseSetting)
          addInput(total)
        }
      return@fold runOnAmplifier(computer)
    })
  }

  fun findBestPossibleConfig(intArray: LongArray, phaseSettings: List<Long>) : Long {
    return generateAllUniqueSequences(phaseSettings)
      .map { getOutputForSettings(intArray, it) }
      .max() ?: throw IllegalStateException("There was no max!")
  }

  private fun setupAmps(memory: LongArray, phaseSettings: List<Long>) : Iterator<Pair<Int, IntcodeComputer>> {
    return phaseSettings.map {
      IntcodeComputer(memory.copyOf())
        .apply { addInput(it) }
    }
      .asSequence()
      .indexedCycleIterator()
  }

  fun getFeedbackForSequence(memory: LongArray, phaseSettings: List<Long>) : Long {
    val amps = setupAmps(memory, phaseSettings)
    var input = 0L
    var ampNumber: Int
    var amp: IntcodeComputer
    do {
      val combo = amps.next()
      ampNumber = combo.first
      amp = combo.second
      amp.addInput(input)
      val originalOutputSize = amp.output.size
      amp.runUntil {
        it.output.size > originalOutputSize || it.current == 99L
      }.let {
        input = it.output.last()
      }
    } while (amp.current != 99L || ampNumber != phaseSettings.size-1)
    return input
  }

  fun findBestPossibleFeedback(memory: LongArray, phaseSettings: List<Long>) : Long {
    return generateAllUniqueSequences(phaseSettings)
      .map { getFeedbackForSequence(memory, it) }
      .max() ?: throw IllegalStateException("There was no max!")
  }
}