package year2021

import framework.Context
import framework.Day

class Day01(context: Context) : Day by context {
    val depths = lines.map { it.toInt() }

    fun part1() = depths.zipWithNext().count { it.first < it.second }
    fun part2() = depths.windowed(4).count { it.first() < it.last() }
}