package com.dennismcdaid.advent2019

object Day02 {

  fun massageData(input: String): IntArray =
    input.trim()
      .split(",")
      .map { it.toInt() }
      .toIntArray()

  fun manipulateData(array: IntArray) : IntArray {
    return array.apply {
      this[1] = 12
      this[2] = 2
    }
  }

  private fun mutate(array: IntArray, opcode: Int, x: Int, y: Int, destination: Int) {
    when (opcode) {
      1 -> array[destination] = array[x] + array[y]
      2 -> array[destination] = array[x] * array[y]
    }
  }

  fun mutateUntilHalt(array: IntArray): IntArray {
    for (i in array.indices step 4) {
      when (val opcode = array[i]) {
        99 -> return array
        in 1..2 -> {
          val (x, y, destination) = array.slice(i+1..i+3)
          mutate(array, opcode, x, y, destination)
        }
        else -> throw UnsupportedOperationException("Unexpected opcode: $opcode")
      }
    }
    throw UnsupportedOperationException("Made it out of the loop. This shouldn't have happened.")
  }

  fun firstRegisterAfterMutation(array: IntArray) : Int =
    mutateUntilHalt(array).first()

  fun getNounVerbMutated(source: IntArray, searchValue: Int) : Int {
    val (noun, verb) = determineNounAndVerb(source, searchValue)
    return 100 * noun + verb
  }


  private fun determineNounAndVerb(source: IntArray, searchValue: Int) : Pair<Int, Int> {
    for (noun in 0..99) {
      for (verb in 0..99) {
        val arrayForRun = IntArray(source.size)
        source.copyInto(arrayForRun)
        arrayForRun[1] = noun
        arrayForRun[2] = verb
        if (firstRegisterAfterMutation(arrayForRun) == searchValue) {
          return Pair(noun, verb)
        }
      }
    }
    throw IllegalStateException("Couldn't find any matches for $searchValue")
  }
}