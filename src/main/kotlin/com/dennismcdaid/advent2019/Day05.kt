package com.dennismcdaid.advent2019

object Day05 {

  sealed class Instruction(val size: Int) {
    abstract val modes: List<Int>
    abstract fun execute(intArray: IntArray, index: Int)

    protected fun getValueForMode(intArray: IntArray, index: Int, mode: Int) : Int {
      return if (mode == 0) intArray[intArray[index]] else intArray[index]
    }

    data class Addition(override val modes: List<Int>) : Instruction(4) {
      override fun execute(intArray: IntArray, index: Int) {
        val a = getValueForMode(intArray, index + 1, modes[0])
        val b = getValueForMode(intArray, index + 2, modes[1])

        intArray[intArray[index + 3]] = a + b
      }
    }

    data class Multiplication(override val modes: List<Int>) : Instruction(4) {
      override fun execute(intArray: IntArray, index: Int) {
        val a = getValueForMode(intArray, index + 1, modes[0])
        val b = getValueForMode(intArray, index + 2, modes[1])

        intArray[intArray[index + 3]] = a * b
      }
    }

    data class Save(val input: Int) : Instruction(2) {
      override val modes: List<Int> = emptyList()
      override fun execute(intArray: IntArray, index: Int) {
        intArray[intArray[index + 1]] = input
      }
    }

    data class Output(override val modes: List<Int>) : Instruction(2) {
      override fun execute(intArray: IntArray, index: Int) {
        // Print when it's not 0
        getOutput(intArray, index).takeIf { it != 0 }?.let(::println)
      }

      fun getOutput(intArray: IntArray, index: Int) : Int {
        return getValueForMode(intArray, index + 1, modes[0])
      }
    }

    object Complete : Instruction(1) {
      override val modes: List<Int> = emptyList()
      override fun execute(intArray: IntArray, index: Int) {}
    }
  }

  fun generateInstruction(command: Int, input: Int = 0) : Instruction {
    val opcode = command % 100
    val modes = (command / 100)
      .toString()
      .let { "000$it" }
      .toCharArray()
      .map(Character::getNumericValue)
      .reversed()
      .subList(0, 3)

    return when (opcode) {
      1 -> Instruction.Addition(modes)
      2 -> Instruction.Multiplication(modes)
      3 -> Instruction.Save(input)
      4 -> Instruction.Output(modes)
      99 -> Instruction.Complete
      else -> throw IllegalArgumentException("No value for opcode $opcode")
    }
  }

  fun execute(intArray: IntArray, input: Int) : Int {
    val outputs = mutableListOf<Int>()
    var instruction: Instruction
    var index = 0
    do {
      instruction = generateInstruction(intArray[index], input)
      instruction.execute(intArray, index)
      (instruction as? Instruction.Output)?.getOutput(intArray, index)?.let(outputs::add)
      index += instruction.size
    } while (instruction !is Instruction.Complete)
    return outputs.last()
  }

}