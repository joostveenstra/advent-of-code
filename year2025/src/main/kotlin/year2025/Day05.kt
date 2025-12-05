package year2025

import framework.Context
import framework.Day
import util.merge
import util.width

class Day05(context: Context) : Day by context {
    val ranges = lines.takeWhile { it.isNotEmpty() }.map { it.split('-').map(String::toLong).let { (a, b) -> a..b } }
    val ids = lines.dropWhile { it.isNotEmpty() }.drop(1).map { it.toLong() }

    fun part1() = ids.count { id -> ranges.any { id in it } }
    fun part2() = ranges.merge().sumOf { it.width }
    
}