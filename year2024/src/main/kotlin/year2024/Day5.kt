package year2024

import framework.Day
import util.EMPTY_LINE
import util.midpoint

object Day5 : Day {
    private fun parse(input: String) = input.split(EMPTY_LINE).let { (r, u) ->
        val rules = r.lines().toSet()
        val updates = u.lines().map { it.split(',') }
        val order = Comparator<String> { a, b ->
            when {
                "$a|$b" in rules -> -1
                "$b|$a" in rules -> 1
                else -> 0
            }
        }
        updates to order
    }

    override fun part1(input: String) = parse(input).let { (updates, order) ->
        updates
            .map { it to it.sortedWith(order) }
            .filter { (update, sorted) -> update == sorted }
            .sumOf { it.second.midpoint().toInt() }
    }

    override fun part2(input: String) = parse(input).let { (updates, order) ->
        updates
            .map { it to it.sortedWith(order) }
            .filterNot { (update, sorted) -> update == sorted }
            .sumOf { it.second.midpoint().toInt() }
    }
}