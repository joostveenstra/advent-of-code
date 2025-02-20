package year2023

import framework.Context
import framework.Day
import util.*

class Day11(context: Context) : Day by context {
    val grid = lines.toGrid { it == '#' }
    val galaxies = grid.findPoints(true)
    val emptyXs = grid.xRange.filter { x -> grid.columnValues(x).none { it } }.toSet()
    val emptyYs = grid.yRange.filter { y -> grid.rowValues(y).none { it } }.toSet()

    fun galaxies(factor: Int): List<Point> = galaxies.map { p ->
        val x = emptyXs.count { it < p.x } * (factor - 1) + p.x
        val y = emptyYs.count { it < p.y } * (factor - 1) + p.y
        Point(x, y)
    }

    fun List<Point>.sumOfDistances() = pairs().sumOf { (a, b) -> (a manhattan b).toLong() }

    fun part1() = galaxies(2).sumOfDistances()
    fun part2() = galaxies(1000000).sumOfDistances()
}