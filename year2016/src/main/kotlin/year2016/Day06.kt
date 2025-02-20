package year2016

import framework.Context
import framework.Day
import util.transpose

class Day06(context: Context) : Day by context {
    val columns = lines.map { it.toList() }.transpose()

    fun List<Char>.frequency() = groupingBy { it }.eachCount().entries.sortedBy { it.value }.map { it.key }

    fun part1() = columns.map { it.frequency().last() }.joinToString("")
    fun part2() = columns.map { it.frequency().first() }.joinToString("")
}