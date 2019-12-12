package com.dennismcdaid.advent2019

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 10")
class Day10Test {

  val input = Parser.readLines("day10_input.txt")

  @Nested
  @DisplayName("Part 1")
  inner class Part1 {

    val example1 =
      ".#..#\n" +
          ".....\n" +
          "#####\n" +
          "....#\n" +
          "...##"

    val example2 =
      "......#.#.\n" +
          "#..#.#....\n" +
          "..#######.\n" +
          ".#.#.###..\n" +
          ".#..#.....\n" +
          "..#....#.#\n" +
          "#..#....#.\n" +
          ".##.#..###\n" +
          "##...#..#.\n" +
          ".#....####"

    val example3 =
      "#.#...#.#.\n" +
          ".###....#.\n" +
          ".#....#...\n" +
          "##.#.#.#.#\n" +
          "....#.#.#.\n" +
          ".##..###.#\n" +
          "..#...##..\n" +
          "..##....##\n" +
          "......#...\n" +
          ".####.###."

    val example4 =
      ".#..#..###\n" +
          "####.###.#\n" +
          "....###.#.\n" +
          "..###.##.#\n" +
          "##.##.#.#.\n" +
          "....###..#\n" +
          "..#.#..#.#\n" +
          "#..#.#.###\n" +
          ".##...##.#\n" +
          ".....#.#.."

    val example5 =
      ".#..##.###...#######\n" +
          "##.############..##.\n" +
          ".#.######.########.#\n" +
          ".###.#######.####.#.\n" +
          "#####.##.#.##.###.##\n" +
          "..#####..#.#########\n" +
          "####################\n" +
          "#.####....###.#.#.##\n" +
          "##.#################\n" +
          "#####.##.###..####..\n" +
          "..######..##.#######\n" +
          "####.##.####...##..#\n" +
          ".#####..#.######.###\n" +
          "##...#.##########...\n" +
          "#.##########.#######\n" +
          ".####.#.###.###.#.##\n" +
          "....##.##.###..#####\n" +
          ".#.#.###########.###\n" +
          "#.#.#.#####.####.###\n" +
          "###.##.####.##.#..##"

    @Test
    fun `Data is parsed as expected`() {
      val oneLineInput = listOf(".#..#")
      val expectedOutput = listOf(
        Point(0, 1), Point(0, 4)
      )
      assertThat(Day10.massageData(oneLineInput)).containsExactlyElementsOf(expectedOutput)
    }

    @Test
    fun `Example data produces expected results`() {
      val input1 = example1.split("\n")
      assertThat(Day10.findMostConnections(input1)).isEqualTo(8)
      val input2 = example2.split("\n")
      assertThat(Day10.findMostConnections(input2)).isEqualTo(33)
      val input3 = example3.split("\n")
      assertThat(Day10.findMostConnections(input3)).isEqualTo(35)
      val input4 = example4.split("\n")
      assertThat(Day10.findMostConnections(input4)).isEqualTo(41)
      val input5 = example5.split("\n")
      assertThat(Day10.findMostConnections(input5)).isEqualTo(210)
    }

    @Test
    fun `Input generates acceptable output`() {
      assertThat(Day10.findMostConnections(input)).isEqualTo(230)
    }
  }
}