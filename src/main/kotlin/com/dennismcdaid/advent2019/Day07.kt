package com.dennismcdaid.advent2019

class Amplifier(private val memory: IntArray, private val phaseSetting: Int) {

  var pointer = 0
  private var phaseSettingUsed = false
  var lastOutput: Int = 0

  fun runUntilOutput(input: Int) : BreakCommand {
    while (memory[pointer] != 99) {
      val nextCommand = Day05.generateInstruction(
        memory[pointer],
        if (!phaseSettingUsed) (phaseSetting).also {
          phaseSettingUsed = true
        } else input
      )
      val newPointer = nextCommand.execute(memory, pointer)
      if (nextCommand is Day05.Instruction.Output)
        return BreakCommand.Output(nextCommand.getOutput(memory, pointer)).also {
          lastOutput = it.output
          pointer = newPointer
        }
      pointer = newPointer
    }
    return BreakCommand.Complete(lastOutput)
  }
}

sealed class BreakCommand {
  abstract val output: Int
  data class Output(override val output: Int) : BreakCommand()
  data class Complete(override val output: Int) : BreakCommand()
}

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

  fun findBestPossibleConfig(intArray: IntArray, phaseSettings: List<Int>) : Int {
    return generateAllUniqueSequences(phaseSettings)
      .map { getOutputForSettings(intArray, it) }
      .max() ?: throw IllegalStateException("There was no max!")
  }

  private fun setupAmps(memory: IntArray, phaseSettings: List<Int>) : Iterator<Pair<Int, Amplifier>> {
    return phaseSettings.map { Amplifier(memory.copyOf(), it) }
      .asSequence()
      .indexedCycleIterator()
  }

  fun getFeedbackForSequence(memory: IntArray, phaseSettings: List<Int>) : Int {
    val amps = setupAmps(memory, phaseSettings)
    var command: BreakCommand
    var input = 0
    var ampNumber: Int
    do {
      val combo = amps.next()
      ampNumber = combo.first
      val amp = combo.second
      command = amp.runUntilOutput(input)
      input = command.output
    } while (command !is BreakCommand.Complete || ampNumber != phaseSettings.size-1)
    return input
  }

  fun findBestPossibleFeedback(memory: IntArray, phaseSettings: List<Int>) : Int {
    return generateAllUniqueSequences(phaseSettings)
      .map { getFeedbackForSequence(memory, it) }
      .max() ?: throw IllegalStateException("There was no max!")
  }
}