package com.dennismcdaid.advent2019

import java.io.File

object Parser {

  fun readLines(fileName: String): List<String> {
    val url = javaClass.classLoader.getResource(fileName)
      ?: throw IllegalStateException("Failed to open $fileName")
    return File(url.toURI()).readLines()
  }
}