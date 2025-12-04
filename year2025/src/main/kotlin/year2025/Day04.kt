package year2025

import framework.Context
import framework.Day
import util.*

class Day04(context: Context) : Day by context {
    val grid = lines.toGrid { it == '@' }.toMutableGrid()

    fun BooleanGrid.canBeRemoved() = points.filter { p -> get(p) && p.allAdjacentElements().count { it } < 4 }

    fun part1() = grid.canBeRemoved().count()
    fun part2() = generateSequence {
        grid.canBeRemoved()
            .onEach { grid.disable(it) }
            .count()
    }.takeWhile { it > 0 }.sum()
}