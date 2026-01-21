package year2025

import framework.Context
import framework.Day
import util.merge
import util.size

class Day05(context: Context) : Day by context {
    val ranges = lines.takeWhile { it.isNotEmpty() }.map {
        it.split('-').let { (a, b) -> a.toLong()..b.toLong() }
    }.merge()
    val ids = lines.dropWhile { it.isNotEmpty() }.drop(1).map { it.toLong() }.sorted()

    fun index(id: Long) = ids.binarySearch(id).let { if (it >= 0) it else -(it + 1) }

    fun part1() = ranges.sumOf { index(it.last + 1) - index(it.first) }
    fun part2() = ranges.sumOf { it.size }
}