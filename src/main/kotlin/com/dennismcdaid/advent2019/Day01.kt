package com.dennismcdaid.advent2019

import org.jetbrains.annotations.TestOnly
import kotlin.math.floor

object Day01 {

  /**
   * Fuel required to launch a given module is based on its mass.
   * Specifically, to find the fuel required for a module, take its mass, divide by three, round down, and subtract 2.
   */
  fun mapMassToFuel(mass: Int) : Int {
    return floor(mass.toDouble() / 3).toInt() - 2
  }

  /**
   * So, for each module mass, calculate its fuel and add it to the total.
   * Then, treat the fuel amount you just calculated as the input mass and repeat the process,
   * continuing until a fuel requirement is zero or negative.
   */
  private fun reduceFuel(totalFuel: Int, currentFuelMass: Int = totalFuel) : Int {
    val newFuel = mapMassToFuel(currentFuelMass)
    return if (newFuel <= 0) totalFuel
    else reduceFuel(totalFuel + newFuel, newFuel)
  }

  fun reduceRawMassToFuel(masses: List<Int>) : Int {
    return masses.map(this::mapMassToFuel).sum()
  }

  fun reduceTotalMassToFuel(masses: List<Int>) : Int {
    return masses.map(this::mapMassToFuel)
      .map { reduceFuel(it) }
      .sum()
  }

  @TestOnly
  fun reduceIndividualMassToFuel(mass: Int) : Int {
    val baseFuel = mapMassToFuel(mass)
    return reduceFuel(baseFuel)
  }
}