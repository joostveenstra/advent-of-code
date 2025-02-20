package year2024

import framework.Context
import framework.Day
import util.EMPTY_LINE
import util.midpoint

class Day05(context: Context) : Day by context {
    val rules = input.split(EMPTY_LINE)[0].lines().toSet()
    val updates = input.split(EMPTY_LINE)[1].lines().map { it.split(',') }
    val order = Comparator<String> { a, b ->
        when {
            "$a|$b" in rules -> -1
            "$b|$a" in rules -> 1
            else -> 0
        }
    }
    val withSorted = updates.map { it to it.sortedWith(order) }

    fun part1() = withSorted.filter { (update, sorted) -> update == sorted }.sumOf { it.second.midpoint().toInt() }
    fun part2() = withSorted.filter { (update, sorted) -> update != sorted }.sumOf { it.second.midpoint().toInt() }
}