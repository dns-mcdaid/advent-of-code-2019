package com.dennismcdaid.advent2019

object Day05 {

  sealed class InstructionResult {
    object Finished : InstructionResult()
    data class Success(val incrementBy: Int) : InstructionResult()
  }

  data class Instruction(val opcode: Int, val modes: List<Int>) {

    fun perform(existing: IntArray, parentIndex: Int) : InstructionResult {
      val params = modes.mapIndexed { index, i ->
        val param = existing[parentIndex+index]
        return@mapIndexed when (i) {
          // Position mode
          0 -> existing[param]
          1 -> param
          else -> throw IllegalArgumentException("Unsupported mode: $i")
        }
      }
      when (opcode) {
        // Addition
        1 -> existing[params[2]] = existing[params[0]] + existing[params[1]]
        // Multiplication
        2 -> existing[params[2]] = existing[params[0]] * existing[params[1]]
        // Write
        // This is custom
        3 -> existing[params[0]] = 1
        // Output
        4 -> println("State: ${existing[params[0]]}")
        // Complete
        99 -> return InstructionResult.Finished
      }

      return InstructionResult.Success(params.size + 1)
    }

    companion object {
      fun from(code: Int) : Instruction {
        val opcode = code % 100
        val rawModes = (code / 100).toString().map(Character::getNumericValue).reversed()

        val modes = if (rawModes.size == sizeForOpcode(opcode)) rawModes
        else rawModes + (rawModes.size until sizeForOpcode(opcode)).map { 0 }

        return Instruction(opcode, modes)
      }

      private fun sizeForOpcode(opcode: Int) : Int = when (opcode) {
        // Addition
        1 -> 3
        // Multiplication
        2 -> 3
        // Write
        // This is custom
        3 -> 1
        // Output
        4 -> 1
        // Complete
        99 -> 0
        else -> throw IllegalArgumentException("Unsupported opcode: $opcode")
      }
    }
  }

}