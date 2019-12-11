package com.dennismcdaid.advent2019.intcode

enum class Operation(private val opcode: Long, val size: Int) {
  ADDITION(1, 4),
  MULTIPLICATION(2, 4),
  SAVE(3, 2),
  OUTPUT(4, 2),
  JUMP_IF_TRUE(5, 3),
  JUMP_IF_FALSE(6, 3),
  LESS_THAN(7, 4),
  EQUAL_TO(8, 4),
  ADJUST_RELATIVE(9, 2),
  HALT_AND_CATCH_FIRE(99, 0);

  companion object {
    fun from(opcode: Long) = values().first { it.opcode == opcode }
  }
}