package year2025

import framework.Context
import framework.Day
import util.allIdentical

class Day02(context: Context) : Day by context {
    val ranges = input.splitToSequence(',').map {
        it.split('-').map(String::toLong).let { (start, end) -> start..end }
    }.flatten()

    fun Long.repeatedTwice() = with(toString()) {
        val half = length / 2
        take(half) == drop(half)
    }

    fun Long.repeatedMultiple() = with(toString()) {
        (1..length / 2).any { size -> length % size == 0 && chunked(size).allIdentical() }
    }

    fun part1() = ranges.filter { it.repeatedTwice() }.sum()
    fun part2() = ranges.filter { it.repeatedMultiple() }.sum()
}