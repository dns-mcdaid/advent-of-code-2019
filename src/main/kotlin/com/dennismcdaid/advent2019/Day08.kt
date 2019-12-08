package com.dennismcdaid.advent2019

/**
 * Images are created as so:
 * [
 *  // Layer 0
 *  [
 *    // Row 0
 *    [1, 2, 3],
 *    // Row 1
 *    [4, 5, 6]
 *  ],
 *  // Layer 1
 *  [
 *    // Row 0
 *    [7, 8, 9],
 *    // Row 1
 *    [0, 1, 2]
 *  ]
 * ]
 */
object Day08 {

  fun massageInput(input: String) : IntArray {
    return input
      .map(Character::getNumericValue)
      .toIntArray()
  }

  fun findLayerWithFewestZeros(image: List<List<List<Int>>>) : Map<Int, Int> {
    return image.map { layer ->
      layer.flatten().groupingBy { it }.eachCount()
    }.minBy { numberMap ->
      numberMap[0] ?: 0
    } ?: throw IllegalStateException("None of them have zero")
  }

  fun buildImage(input: IntArray, width: Int, height: Int) : List<List<List<Int>>> {
    val image = mutableListOf<List<List<Int>>>()
    for (i in input.indices step width * height) {
      val layer = mutableListOf<List<Int>>()
      for (j in i until i + width * height step width) {
        layer.add(input.copyOfRange(j, j + width).toList())
      }
      image.add(layer)
    }
    return image
  }

  fun getCalculationFromZeroLayer(input: IntArray, width: Int, height: Int) : Int {
    return buildImage(input, width, height)
      .let(this::findLayerWithFewestZeros)
      .let {
        (it[1] ?: 0) * (it[2] ?: 0)
      }
  }

  fun mergeLayers(image: List<List<List<Int>>>, width: Int, height: Int) : List<List<Int>> {
    val layers = image.map { layer ->
      layer.flatten()
    }
    val container = (0 until width * height).map { i ->
      layers.asSequence().firstOrNull { it[i] != 2 }?.get(i) ?: 2
    }.toIntArray()

    val condensedImage = mutableListOf<List<Int>>()

    for (i in container.indices step width) {
      condensedImage.add(container.copyOfRange(i, i + width).toList())
    }

    return condensedImage
  }

  fun makePrintable(image: List<List<Int>>) : List<String> {
    return image.map { row ->
      row.joinToString(separator = " ") {
        return@joinToString when (it) {
          1 -> "X"
          else -> " "
        }
      }
    }
  }
}