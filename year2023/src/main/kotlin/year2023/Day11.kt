package year2023

import framework.Day
import util.Point
import util.combinations
import util.toCharGrid

object Day11 : Day<Long> {
    private fun String.toGalaxies(factor: Int): List<Point> {
        val grid = toCharGrid()

        fun expand(size: Int, empty: Set<Int>) = (0..<size - 1).scan(0) { prefix, i -> prefix + if (i in empty) factor else 1 }

        val emptyXs = grid.ys.filter { y -> grid.getRow(y).none { it == '#' } }.toSet()
        val emptyYs = grid.xs.filter { x -> grid.getColumn(x).none { it == '#' } }.toSet()
        val xs = expand(grid.width, emptyYs)
        val ys = expand(grid.height, emptyXs)

        return buildList {
            ys.zip(grid.rows).forEach { (y, row) ->
                xs.zip(row).forEach { (x, char) ->
                    if (char == '#') add(Point(x, y))
                }
            }
        }
    }

    private fun List<Point>.sumOfDistances() = combinations(2).sumOf { (a, b) -> a.manhattan(b).toLong() }

    override fun part1(input: String) = input.toGalaxies(2).sumOfDistances()

    override fun part2(input: String) = input.toGalaxies(1000000).sumOfDistances()
}