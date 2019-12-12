package com.dennismcdaid.advent2019

import kotlin.math.abs

data class Position(val x: Int, val y: Int, val z: Int) {

  fun copyWith(velocity: Velocity) : Position {
    return Position(x + velocity.x, y + velocity.y, z + velocity.z)
  }

  fun getPotentialEnergy() : Int {
    return abs(x) + abs(y) + abs(z)
  }
}
data class Velocity(val x: Int, val y: Int, val z: Int) {

  fun copyWith(x: Int, y: Int, z: Int) : Velocity {
    return Velocity(this.x + x, this.y + y, this.z + z)
  }

  fun getKineticEnergy() : Int {
    return abs(x) + abs(y) + abs(z)
  }

  companion object {
    val STARTING = Velocity(0, 0, 0)
  }
}

data class Moon(val position: Position, val velocity: Velocity) {

  fun getEnergy() : Int {
    return position.getPotentialEnergy() * velocity.getKineticEnergy()
  }
}

object Day12 {

  fun simulateMotion(moons: List<Moon>) : List<Moon> {
    return moons.map { moon ->
      val newVelocity = applyGravity(moon, moons.filter { it != moon })
      return@map Moon(moon.position.copyWith(newVelocity), newVelocity)
    }
  }

  fun simulateMotionRepeatedly(moons: List<Moon>, timeSteps: Int) : List<Moon> {
    var innerMoons: List<Moon> = moons
    for (i in 0 until timeSteps) {
      innerMoons = simulateMotion(innerMoons)
    }
    return innerMoons
  }

  fun getEnergyAfterMotions(moons: List<Moon>, timeSteps: Int) : Int {
    val updatedMoons = simulateMotionRepeatedly(moons, timeSteps)
    return getTotalEnergy(updatedMoons)
  }

  fun getTotalEnergy(moons: List<Moon>) = moons.map(Moon::getEnergy).sum()

  private fun applyGravity(moon: Moon, others: List<Moon>) : Velocity {
    val p = moon.position
    val ps = others.map { it.position }
    val x = getAdjustedValue(p.x, ps.map { it.x })
    val y = getAdjustedValue(p.y, ps.map { it.y })
    val z = getAdjustedValue(p.z, ps.map { it.z })
    return moon.velocity.copyWith(x, y, z)
  }

  private fun getAdjustedValue(subject: Int, others: List<Int>) = others.map {
    return@map when {
      it > subject -> 1
      it < subject -> -1
      else -> 0
    }
  }.sum()

  fun moonsFromInput(input: List<String>) : List<Moon> {
    val matcher = Regex("-?[0-9]\\d*")

    return input.map { line ->
      val (x, y, z) = matcher.findAll(line).map { it.value.toInt() }.toList()
      Moon(Position(x, y, z), Velocity.STARTING)
    }
  }
}