package year2022

import framework.Day
import util.contains
import util.overlaps

object Day4 : Day {
    private fun String.toRangePairs() = lines().map {
        it.split(',').map { range ->
            range.split('-').let { (min, max) -> min.toInt()..max.toInt() }
        }
    }

    override fun part1(input: String) = input.toRangePairs().count { (a, b) -> a in b || b in a }

    override fun part2(input: String) = input.toRangePairs().count { (a, b) -> a overlaps b }
}