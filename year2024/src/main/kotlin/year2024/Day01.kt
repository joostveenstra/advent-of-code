package year2024

import framework.Context
import framework.Day
import util.allInts
import util.toPair
import kotlin.math.absoluteValue

class Day01(context: Context) : Day by context {
    val lists = input.allInts().chunked(2).map { it.toPair() }.unzip()

    fun part1() = lists.let { (left, right) ->
        (left.sorted() zip right.sorted()).sumOf { (l, r) -> (r - l).absoluteValue }
    }

    fun part2() = lists.let { (left, right) ->
        val frequencies = right.groupingBy { it }.eachCount()
        left.sumOf { l -> l * frequencies.getOrDefault(l, 0) }
    }
}