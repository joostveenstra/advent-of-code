package year2016

import framework.Context
import framework.Day
import util.frequencies
import util.transpose

class Day06(context: Context) : Day by context {
    val columns = lines.map { it.toList() }.transpose()
    val common = columns.map { c ->
        c.frequencies().entries.sortedBy { it.value }.map { it.key }
    }

    fun part1() = common.map { it.last() }.joinToString("")
    fun part2() = common.map { it.first() }.joinToString("")
}