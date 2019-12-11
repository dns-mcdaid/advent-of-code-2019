package com.dennismcdaid.advent2019.intcode

enum class Mode(private val rawValue: Int) {
  POSITION(0),
  IMMEDIATE(1),
  RELATIVE(2);

  companion object {
    fun from(value: Int) = values().first { it.rawValue == value }
  }
}