package com.dennismcdaid.advent2019

object Day10 {
  fun massageData(input: List<String>) : List<Point> {
    return input.mapIndexed { x, s ->
      s.toCharArray().mapIndexed { y, c ->
        Pair(Point(x, y), c)
      }
    }
      .flatten()
      .filter { it.second == ASTEROID }
      .map { it.first }
  }

  private fun findBestPossibleLocation(points: List<Point>) : Pair<Point, Int> {
    return points.map { candidate ->
      val othersItCanSee = points.filter { it != candidate }.map {
        candidate.angle(it)
      }.toSet().size
      return@map Pair(candidate, othersItCanSee)
    }
      .maxBy { it.second } ?: throw IllegalStateException("There was no max!")
  }

  fun findMostConnections(input: List<String>) : Int {
    return massageData(input)
      .let(this::findBestPossibleLocation).second
  }

  fun find200thVaporized(input: List<String>) {
    val asteroids = massageData(input)
    val outpost = findBestPossibleLocation(asteroids).first
    vaporizedOrder(outpost, asteroids)
  }

  fun vaporizedOrder(outpost: Point, asteroids: List<Point>) {
    val thetas = asteroids.filter { it != outpost }.map {
      outpost.angle(it)
    }
    println(thetas)
  }


  private const val ASTEROID = '#'
}