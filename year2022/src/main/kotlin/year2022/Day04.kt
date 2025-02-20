package year2022

import framework.Context
import framework.Day
import util.contains
import util.overlaps

class Day04(context: Context) : Day by context {
    val rangePairs = lines.map {
        it.split(',').map { range ->
            range.split('-').let { (min, max) -> min.toInt()..max.toInt() }
        }
    }

    fun part1() = rangePairs.count { (a, b) -> a in b || b in a }
    fun part2() = rangePairs.count { (a, b) -> a overlaps b }
}