package com.dennismcdaid.advent2019.intcode

sealed class Instruction(val size: Int) {
  abstract val modes: List<Int>
  abstract fun execute(intArray: IntArray, index: Int): Int

  protected fun getValueForMode(intArray: IntArray, index: Int, mode: Int): Int {
    return if (mode == 0) intArray[intArray[index]] else intArray[index]
  }

  data class Addition(override val modes: List<Int>) : Instruction(4) {
    override fun execute(intArray: IntArray, index: Int): Int {
      val a = getValueForMode(intArray, index + 1, modes[0])
      val b = getValueForMode(intArray, index + 2, modes[1])

      intArray[intArray[index + 3]] = a + b
      return index + size
    }
  }

  data class Multiplication(override val modes: List<Int>) : Instruction(4) {
    override fun execute(intArray: IntArray, index: Int): Int {
      val a = getValueForMode(intArray, index + 1, modes[0])
      val b = getValueForMode(intArray, index + 2, modes[1])

      intArray[intArray[index + 3]] = a * b
      return index + size
    }
  }

  data class Save(val input: Int) : Instruction(2) {
    override val modes: List<Int> = emptyList()
    override fun execute(intArray: IntArray, index: Int): Int {
      intArray[intArray[index + 1]] = input
      return index + size
    }
  }

  data class Output(override val modes: List<Int>) : Instruction(2) {
    override fun execute(intArray: IntArray, index: Int): Int {
      return index + size
    }

    fun getOutput(intArray: IntArray, index: Int): Int {
      return getValueForMode(intArray, index + 1, modes[0])
    }
  }

  data class Jump(val condition: Boolean, override val modes: List<Int>) : Instruction(3) {
    override fun execute(intArray: IntArray, index: Int): Int {
      val toCheck = getValueForMode(intArray, index + 1, modes[0])
      val potentialNewIndex = getValueForMode(intArray, index + 2, modes[1])

      val passes = toCheck != 0 == condition

      return if (passes) potentialNewIndex else index + size
    }
  }

  data class LT(override val modes: List<Int>) : Instruction(4) {
    override fun execute(intArray: IntArray, index: Int): Int {
      val a = getValueForMode(intArray, index + 1, modes[0])
      val b = getValueForMode(intArray, index + 2, modes[1])
      intArray[intArray[index + 3]] = if (a < b) 1 else 0
      return index + size
    }
  }

  data class Equals(override val modes: List<Int>) : Instruction(4) {
    override fun execute(intArray: IntArray, index: Int): Int {
      val a = getValueForMode(intArray, index + 1, modes[0])
      val b = getValueForMode(intArray, index + 2, modes[1])
      intArray[intArray[index + 3]] = if (a == b) 1 else 0
      return index + size
    }
  }

  object Complete : Instruction(1) {
    override val modes: List<Int> = emptyList()
    override fun execute(intArray: IntArray, index: Int): Int {
      return index + size
    }
  }

  companion object {
    fun from(command: Int, input: Int = 0): Instruction {
      val opcode = command % 100
      val modes = (command / 100)
        .toString()
        .let { "000$it" }
        .toCharArray()
        .map(Character::getNumericValue)
        .reversed()
        .subList(0, 3)

      return when (opcode) {
        1 -> Addition(modes)
        2 -> Multiplication(modes)
        3 -> Save(input)
        4 -> Output(modes)
        5 -> Jump(true, modes)
        6 -> Jump(false, modes)
        7 -> LT(modes)
        8 -> Equals(modes)
        99 -> Complete
        else -> throw IllegalArgumentException("No value for opcode $opcode")
      }
    }
  }
}