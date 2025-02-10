package year2024

import framework.Day
import util.*

object Day10 : Day {
    private fun IntGrid.countTrails(head: Point, distinct: Boolean): Int {
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

    private fun IntGrid.score(distinct: Boolean) = findPoints(0).sumOf { countTrails(it, distinct) }

    override fun part1(input: String) = input.toDigitGrid().score(false)

    override fun part2(input: String) = input.toDigitGrid().score(true)
}