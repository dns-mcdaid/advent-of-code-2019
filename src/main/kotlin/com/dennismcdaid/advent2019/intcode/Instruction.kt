package com.dennismcdaid.advent2019.intcode

data class Instruction(val operation: Operation, val modes: List<Mode>) {

  companion object {
    fun from(command: Int): Instruction {
      val opcode = command % 100
      val modes = (command / 100)
        .toString()
        .let { "000$it" }
        .map(Character::getNumericValue)
        .reversed()
        .subList(0, 3)

      return Instruction(Operation.from(opcode), modes.map { Mode.from(it) })
    }
  }
}