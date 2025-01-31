package year2023

import framework.Day
import util.*

object Day11 : Day {
    private fun String.toGalaxies(factor: Int): List<Point> {
        val grid = lines().toGrid { it == '#' }
        val galaxies = grid.findPoints(true)

        val emptyXs = grid.xRange.filter { x -> grid.columnValues(x).none { it } }.toSet()
        val emptyYs = grid.yRange.filter { y -> grid.rowValues(y).none { it } }.toSet()

        return galaxies.map { p ->
            val x = emptyXs.count { it < p.x } * (factor - 1) + p.x
            val y = emptyYs.count { it < p.y } * (factor - 1) + p.y
            Point(x, y)
        }
    }

    private fun List<Point>.sumOfDistances() = pairs().sumOf { (a, b) -> a.manhattan(b).toLong() }

    override fun part1(input: String) = input.toGalaxies(2).sumOfDistances()

    override fun part2(input: String) = input.toGalaxies(1000000).sumOfDistances()
}