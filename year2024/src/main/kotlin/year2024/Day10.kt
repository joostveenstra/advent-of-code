package year2024

import framework.Context
import framework.Day
import util.*

class Day10(context: Context) : Day by context {
    val grid = input.toDigitGrid()
    val heads = grid.findPoints(0)

    fun IntGrid.countTrails(head: Point, distinct: Boolean): Int {
        val seen = asMutableBooleanGrid()

        fun step(position: Point): Int =
            when (val height = get(position)) {
                9 -> 1
                else -> position.cardinalNeighbours
                    .filter { getOrNull(it) == height + 1 && (distinct || !seen[it]) }
                    .onEach(seen::enable)
                    .sumOf(::step)
            }

        return step(head)
    }

    fun score(distinct: Boolean) = heads.sumOf { grid.countTrails(it, distinct) }

    fun part1() = score(false)
    fun part2() = score(true)
}