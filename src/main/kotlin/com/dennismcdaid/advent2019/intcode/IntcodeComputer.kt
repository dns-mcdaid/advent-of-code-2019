package com.dennismcdaid.advent2019.intcode

import java.util.*

class IntcodeComputer(rawMemory: LongArray) {
  private val maxSize = rawMemory.size.coerceAtLeast(100000)

  private val memory = LongArray(maxSize).apply {
    rawMemory.copyInto(this)
  }
  var pointer = 0
  var relativePointer = 0
  val output = mutableListOf<Long>()
  private val inputQueue = ArrayDeque<Long>()

  fun addInput(input: Long) {
    inputQueue.add(input)
  }

  operator fun get(index: Int): Long {
    return memory[index]
  }

  val current: Long
    get() = memory[pointer]

  fun <T> runThenGet(thenReturn: (IntcodeComputer) -> T): T {
    var pass = 0
    while (memory[pointer] != 99L) {
      val instruction = Instruction.from(memory[pointer])
      execute(instruction)
      increment(instruction)
      pass++
    }
    return thenReturn(this@IntcodeComputer)
  }

  fun runUntil(condition: (IntcodeComputer) -> Boolean): IntcodeComputer {
    var pass = 0
    while (!condition(this)) {
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
        writeToThirdRegister(a + b, instruction)
      }
      Operation.MULTIPLICATION -> {
        val (a, b) = getValuesForModes(instruction.modes)
        writeToThirdRegister(a * b, instruction)
      }
      Operation.LESS_THAN -> {
        val (a, b) = getValuesForModes(instruction.modes)
        writeToThirdRegister(if (a < b) 1 else 0, instruction)
      }
      Operation.EQUAL_TO -> {
        val (a, b) = getValuesForModes(instruction.modes)
        writeToThirdRegister(if (a == b) 1 else 0, instruction)
      }
      Operation.SAVE -> {
        val writeIndex = getWriteIndex(memory[pointer + 1].toInt(), instruction.modes.first())
        memory[writeIndex] = inputQueue.pop()
      }
      Operation.OUTPUT -> {
        val valueToOutput = getValueForMode(instruction.modes.first(), pointer + 1)
        output.add(valueToOutput)
      }
      Operation.ADJUST_RELATIVE -> {
        val toAdd = getValueForMode(instruction.modes.first(), pointer + 1).toInt()
        relativePointer += toAdd
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
        if (toCheck != 0L) (potentialNewIndex.toInt()) else pointer + Operation.JUMP_IF_TRUE.size
      }
      Operation.JUMP_IF_FALSE -> {
        val (toCheck, potentialNewIndex) = getValuesForModes(instruction.modes)
        if (toCheck == 0L) (potentialNewIndex.toInt()) else pointer + Operation.JUMP_IF_TRUE.size
      }
      else -> pointer + instruction.operation.size
    }
  }

  private fun writeToThirdRegister(newValue: Long, instruction: Instruction) {
    val writeIndex = getWriteIndex(memory[pointer + 3].toInt(), instruction.modes.last())
    memory[writeIndex] = newValue
  }

  private fun getWriteIndex(index: Int, mode: Mode) = when (mode) {
    Mode.POSITION -> index
    Mode.IMMEDIATE -> throw IllegalArgumentException("Can't be in immediate mode!")
    Mode.RELATIVE -> index + relativePointer
  }

  private fun getValuesForModes(modes: List<Mode>) = modes.take(2).mapIndexed { index, i ->
    getValueForMode(i, pointer + index + 1)
  }

  private fun getValueForMode(mode: Mode, index: Int): Long {
    val item = memory[index]
    return when (mode) {
      Mode.POSITION -> memory[item.toInt()]
      Mode.IMMEDIATE -> item
      Mode.RELATIVE -> memory[item.toInt() + relativePointer]
    }
  }
}