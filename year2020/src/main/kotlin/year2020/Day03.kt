package year2020

import framework.Context
import framework.Day
import util.*

class Day03(context: Context) : Day by context {
    val grid = lines.toGrid { it == '#' }

    fun BooleanGrid.traverse(slope: Point) =
        generateSequence(ORIGIN) { it + slope }
            .map { it.copy(x = it.x % width) }
            .takeWhile { it in this }
            .count { get(it) }
            .toLong()

    fun part1() = grid.traverse(Point(3, 1))
    fun part2() = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2).map { grid.traverse(it.toPoint()) }.product()
}