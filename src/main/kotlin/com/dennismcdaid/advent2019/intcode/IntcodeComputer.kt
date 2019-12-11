package com.dennismcdaid.advent2019.intcode

import java.util.*

class IntcodeComputer(private val memory: LongArray) {
  var pointer = 0
  var relativePointer = 0
  val output = mutableListOf<Long>()
  private val inputQueue = ArrayDeque<Long>()

  fun addInput(input: Long) {
    inputQueue.add(input)
  }

  operator fun get(index: Int) : Long {
    return memory[index]
  }

  val current : Long
    get() = memory[pointer]

  fun runThenGet(thenReturn: (IntcodeComputer) -> Long): Long {
    var pass = 0
    while (memory[pointer] != 99L) {
      val instruction = Instruction.from(memory[pointer])
      execute(instruction)
      increment(instruction)
      pass++
    }
    return thenReturn(this@IntcodeComputer)
  }

  fun runUntil(condition: (IntcodeComputer) -> Boolean) : IntcodeComputer {
    var pass = 0
    while(!condition(this)) {
      val instruction = Instruction.from(memory[pointer])
      execute(instruction)
      increment(instruction)
      pass++
    }
    return this
  }

  private fun execute(instruction: Instruction) {
    when (instruction.operation) {
      Operation.ADDITION -> {
        val (a, b) = getValuesForModes(instruction.modes)
        writeToThirdRegister(a + b)
      }
      Operation.MULTIPLICATION -> {
        val (a, b) = getValuesForModes(instruction.modes)
        writeToThirdRegister(a * b)
      }
      Operation.LESS_THAN -> {
        val (a, b) = getValuesForModes(instruction.modes)
        writeToThirdRegister(if (a < b) 1 else 0)
      }
      Operation.EQUAL_TO -> {
        val (a, b) = getValuesForModes(instruction.modes)
        writeToThirdRegister(if (a == b) 1 else 0)
      }
      Operation.SAVE -> {
        memory[memory[pointer + 1].toInt()] = inputQueue.pop()
      }
      Operation.OUTPUT -> {
        output.add(getValueForMode(instruction.modes.first(), pointer + 1))
      }
      Operation.ADJUST_RELATIVE -> {
        relativePointer += getValueForMode(instruction.modes.first(), pointer + 1).toInt()
      }
      Operation.JUMP_IF_TRUE,
      Operation.JUMP_IF_FALSE,
      Operation.HALT_AND_CATCH_FIRE -> {
        // no-op
      }
    }
  }

  private fun increment(instruction: Instruction) {
    pointer = when (instruction.operation) {
      Operation.JUMP_IF_TRUE -> {
        val (toCheck, potentialNewIndex) = getValuesForModes(instruction.modes)
        if (toCheck != 0L) potentialNewIndex.toInt() else pointer + Operation.JUMP_IF_TRUE.size
      }
      Operation.JUMP_IF_FALSE -> {
        val (toCheck, potentialNewIndex) = getValuesForModes(instruction.modes)
        if (toCheck == 0L) potentialNewIndex.toInt() else pointer + Operation.JUMP_IF_TRUE.size
      }
      else -> pointer + instruction.operation.size
    }
  }

  private fun writeToThirdRegister(newValue: Long) {
    val writeIndex = memory[pointer + 3].toInt()
    memory[writeIndex] = newValue
  }

  private fun getValuesForModes(modes: List<Mode>) = modes.take(2).mapIndexed { index, i ->
    getValueForMode(i, pointer + index + 1)
  }

  private fun getValueForMode(mode: Mode, index: Int) : Long {
    val item = memory[index]
    return when (mode) {
      Mode.POSITION -> memory[item.toInt()]
      Mode.IMMEDIATE -> item
      Mode.RELATIVE -> memory[item.toInt() + relativePointer]
    }
  }
}