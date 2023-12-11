package year2023

import framework.Day
import util.Point
import util.combinations

object Day11 : Day<Long> {
    private fun String.toGalaxies(factor: Int): List<Point> {
        val grid = lines()
        val maxX = grid.first().length
        val maxY = grid.size
        val emptyRows = (0..<maxY).filter { y -> grid[y].none { it == '#' } }.toSet()
        val emptyCols = (0..<maxX).filter { x -> grid.map { row -> row[x] }.none { it == '#' } }.toSet()
        val xs = (0..<maxX - 1).scan(0) { prefix, x -> prefix + if (x in emptyCols) factor else 1 }
        val ys = (0..<maxY - 1).scan(0) { prefix, y -> prefix + if (y in emptyRows) factor else 1 }
        return buildList {
            ys.zip(0..<maxY).forEach { (y, row) ->
                xs.zip(grid[row].asIterable()).forEach { (x, char) ->
                    if (char == '#') add(Point(x, y))
                }
            }
        }
    }

    private fun List<Point>.sumOfDistances() = combinations(2).sumOf { (a, b) -> a.manhattan(b).toLong() }

    override fun part1(input: String) = input.toGalaxies(2).sumOfDistances()

    override fun part2(input: String) = input.toGalaxies(1000000).sumOfDistances()
}