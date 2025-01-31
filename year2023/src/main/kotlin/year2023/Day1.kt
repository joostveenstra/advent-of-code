package year2023

import framework.Day

object Day1 : Day {
    private val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    private val groups = digits.withIndex().groupBy { (_, digit) -> digit.first() }

    private fun List<Int>.calibrate() = 10 * first() + last()

    override fun part1(input: String) = input.lines().sumOf { line ->
        line.mapNotNull { it.digitToIntOrNull() }.calibrate()
    }

    override fun part2(input: String) = input.lines().sumOf { line ->
        line.mapIndexedNotNull { i, char ->
            char.digitToIntOrNull() ?: groups[char]?.find { (_, digit) -> line.regionMatches(i, digit, 0, digit.length) }?.let { it.index + 1 }
        }.calibrate()
    }
}