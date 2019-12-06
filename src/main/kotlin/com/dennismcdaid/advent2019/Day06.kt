package com.dennismcdaid.advent2019

object Day06 {
  data class Orbital(val name: String, val connections: MutableList<Orbital> = mutableListOf())

  fun parseOrbit(orbitPair: String) : List<String> = orbitPair.split(")")

  fun mapOrbits(orbitList: List<String>, inverse: Boolean = false) : Map<String, Orbital> {
    val orbitMap = mutableMapOf<String, Orbital>()
    orbitList.asSequence()
      .map(this::parseOrbit)
      .forEach { (parentStr, childStr) ->
        val child = orbitMap[childStr] ?: Orbital(childStr).also {
          orbitMap[childStr] = it
        }
        val parent = orbitMap[parentStr] ?: Orbital(parentStr).also {
          orbitMap[parentStr] = it
        }
        if (inverse) child.connections.add(parent) else parent.connections.add(child)
      }

    return orbitMap
  }

  // TODO: Handle this off by one stuff better.
  fun getOrbitsRecursively(parent: Orbital, depth: Int = 0) : Int {
    val toAdd = if (depth == 0) parent.connections.size else parent.connections.size + depth - 1
    return parent.connections
      .map { getOrbitsRecursively(it, depth + 1) }
      .sum() + toAdd
  }

  fun getNumberOfOrbits(orbitList: List<String>) : Int {
    val orbitMap = mapOrbits(orbitList)
    val center = orbitMap[CENTER] ?: throw IllegalStateException("No center was provided!")
    return getOrbitsRecursively(center, 0)
  }

  fun findDistanceToSanta(orbitList: List<String>) : Int {
    val orbitMap = mapOrbits(orbitList, inverse = true)
    val you = orbitMap[YOU] ?: throw IllegalStateException("You weren't provided!")
    val santa = orbitMap[SANTA] ?: throw IllegalStateException("Santa wasn't provided!")

    val connections = mutableMapOf<String, Int>()

    var distance = 0
    var yourParents = you.connections
    var santasParents = santa.connections
    var found = false
    do {
      val parentNames = yourParents.map { it.name } + santasParents.map { it.name }
      val potentialConnection = parentNames.firstOrNull { it in connections }

      if (potentialConnection != null) {
        distance += connections[potentialConnection] ?: throw IllegalStateException("This shouldn't have happened!")
        found = true
        break
      }

      parentNames.forEach {
        connections[it] = distance
      }

      distance++
      yourParents = yourParents.flatMap { it.connections }.toMutableList()
      santasParents = santasParents.flatMap { it.connections }.toMutableList()
    } while (!found)

    return distance
  }

  const val CENTER = "COM"
  private const val YOU = "YOU"
  private const val SANTA = "SAN"
}