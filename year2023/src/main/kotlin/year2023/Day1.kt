package year2023

import framework.Day
import util.zipWithIndex

object Day1 : Day<Int> {
    private val map = listOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine").zipWithIndex().toMap()
    private val regex = """(?=(one|two|three|four|five|six|seven|eight|nine|\d))""".toRegex()

    private fun List<Int>.calibrate() = 10 * first() + last()

    override fun part1(input: String) = input.lines().sumOf { line ->
        val numbers = line.filter { it.isDigit() }.map { it.digitToInt() }
        numbers.calibrate()
    }

    override fun part2(input: String) = input.lines().sumOf { line ->
        val tokens = regex.findAll(line).flatMap { it.destructured.toList() }.toList()
        val numbers = tokens.map { map[it] ?: it.toInt() }
        numbers.calibrate()
    }
}