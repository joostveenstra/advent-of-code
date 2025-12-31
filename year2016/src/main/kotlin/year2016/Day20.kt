package year2016

import framework.Context
import framework.Day
import util.size

class Day20(context: Context) : Day by context {
    val ranges = lines.map {
        it.split('-').let { (first, last) -> first.toUInt()..last.toUInt() }
    }

    infix fun UIntRange.subtract(other: UIntRange) = when {
        first >= other.first && last <= other.last -> listOf()
        first < other.first && last > other.last -> listOf(first..other.first - 1u, other.last + 1u..last)
        first < other.first && last >= other.first -> listOf(first..other.first - 1u)
        first <= other.last && last > other.last -> listOf(other.last + 1u..last)
        else -> listOf(this)
    }

    fun List<UIntRange>.intersect() = fold(listOf(0u..UInt.MAX_VALUE)) { remaining, range -> remaining.flatMap { it subtract range } }

    val allowed = ranges.intersect()

    fun part1() = allowed.minOf { it.first }
    fun part2() = allowed.sumOf { it.size }
}