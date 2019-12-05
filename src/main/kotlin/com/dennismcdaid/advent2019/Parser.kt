package com.dennismcdaid.advent2019

import java.io.File

object Parser {

  private fun getFile(fileName: String): File {
    val url = javaClass.classLoader.getResource(fileName)
      ?: throw IllegalStateException("Failed to open $fileName")
    return File(url.toURI())
  }

  fun readLines(fileName: String): List<String> = getFile(fileName).readLines()

  fun read(fileName: String): String = getFile(fileName).readText().trim()

  fun readInts(fileName: String): IntArray = read(fileName)
    .split(",")
    .map(String::toInt)
    .toIntArray()

}