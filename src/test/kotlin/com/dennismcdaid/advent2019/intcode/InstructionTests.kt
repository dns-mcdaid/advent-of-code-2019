package com.dennismcdaid.advent2019.intcode

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class InstructionTests {

  @Test
  fun `Instruction generates correctly`() {
    Assertions.assertThat(Instruction.from(3)).isInstanceOf(Instruction.Save::class.java)
    Assertions.assertThat(Instruction.from(4)).isInstanceOf(Instruction.Output::class.java)
    Assertions.assertThat(Instruction.from(1)).isInstanceOf(Instruction.Addition::class.java)
    Assertions.assertThat(Instruction.from(1002)).isInstanceOf(Instruction.Multiplication::class.java)
    Assertions.assertThat(Instruction.from(99)).isInstanceOf(Instruction.Complete::class.java)
    Assertions.assertThat(Instruction.from(1002).modes).isEqualTo(listOf(0, 1, 0))
    Assertions.assertThat(Instruction.from(11101).modes).isEqualTo(listOf(1, 1, 1))
  }

  @Test
  fun `Operation performs correctly`() {
    val example = intArrayOf(1002, 4, 3, 4, 33)
    val expectedOutput = intArrayOf(1002, 4, 3, 4, 99)
    val index = 0
    val instruction = Instruction.from(example[index])
    Assertions.assertThat(instruction).isInstanceOf(Instruction.Multiplication::class.java)
    Assertions.assertThat(instruction.modes).isEqualTo(listOf(0, 1, 0))
    instruction.execute(example, index)
    Assertions.assertThat(example).isEqualTo(expectedOutput)
  }

  @Test
  fun `New instructions generate correctly`() {
    Assertions.assertThat(Instruction.from(5)).isInstanceOf(Instruction.Jump::class.java)
    Assertions.assertThat(Instruction.from(6)).isInstanceOf(Instruction.Jump::class.java)
    Assertions.assertThat((Instruction.from(5) as Instruction.Jump).condition).isEqualTo(true)
    Assertions.assertThat((Instruction.from(6) as Instruction.Jump).condition).isEqualTo(false)
  }

  @Test
  fun `Jump if true instruction handles as expected`() {
    val jIfTrueList = intArrayOf(1105, 2, 47)
    val falseList = intArrayOf(1105, 0, 13)
    val instruction = Instruction.from(jIfTrueList[0])
    Assertions.assertThat(instruction.execute(jIfTrueList, 0)).isEqualTo(47)
    Assertions.assertThat(instruction.execute(falseList, 0)).isEqualTo(instruction.size)
  }

  @Test
  fun `Jump if false instruction handles as expected`() {
    val nonzero = intArrayOf(1106, 2, 47)
    val zero = intArrayOf(1106, 0, 13)
    val instruction = Instruction.from(nonzero[0])
    Assertions.assertThat(instruction.execute(nonzero, 0)).isEqualTo(instruction.size)
    Assertions.assertThat(instruction.execute(zero, 0)).isEqualTo(13)
  }
}