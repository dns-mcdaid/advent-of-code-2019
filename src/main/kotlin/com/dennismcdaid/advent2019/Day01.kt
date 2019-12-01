package com.dennismcdaid.advent2019

import kotlin.math.floor

object Day01 {

  /**
   * Fuel required to launch a given module is based on its mass.
   * Specifically, to find the fuel required for a module, take its mass, divide by three, round down, and subtract 2.
   */
  fun calculateFuelForMass(mass: Int) : Int {
    return floor(mass.toDouble() / 3).toInt() - 2
  }

  fun calculateTotalFuel(masses: List<Int>) : Int {
    return masses.map(this::calculateFuelForMass).sum()
  }
}