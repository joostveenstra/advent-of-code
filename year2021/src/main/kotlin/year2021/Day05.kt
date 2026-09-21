package year2021

import framework.Context
import framework.Day
import util.*

class Day05(context: Context) : Day by context {
    val vents = input.allInts().chunked(4).map { (a, b, c, d) -> Point(a, b)..Point(c, d) }.toList()

    fun List<Line>.overlapping() = flatMap { it.allPoints() }.frequencies().count { it.value > 1 }

    fun part1() = vents.filter { it.isStraight }.overlapping()
    fun part2() = vents.overlapping()
}