package year2020

import framework.Context
import framework.Day
import util.EMPTY_LINE
import util.frequencies

class Day06(context: Context) : Day by context {
    val groups = input.split(EMPTY_LINE).map { group ->
        group.lines().let { it.joinToString("") to it.size }
    }

    fun part1() = groups.sumOf { (g) -> g.toSet().size }
    fun part2() = groups.sumOf { (g, size) -> g.frequencies().count { it.value == size } }
}