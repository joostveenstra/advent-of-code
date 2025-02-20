package year2023

import framework.Context
import framework.Day

class Day01(context: Context) : Day by context {
    val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val groups = digits.withIndex().groupBy { (_, digit) -> digit.first() }

    fun List<Int>.calibrate() = if (isNotEmpty()) 10 * first() + last() else 0

    fun part1() = lines.sumOf { line ->
        line.mapNotNull { it.digitToIntOrNull() }.calibrate()
    }

    fun part2() = lines.sumOf { line ->
        line.mapIndexedNotNull { i, char ->
            char.digitToIntOrNull() ?: groups[char]?.find { (_, digit) -> line.regionMatches(i, digit, 0, digit.length) }?.let { it.index + 1 }
        }.calibrate()
    }
}