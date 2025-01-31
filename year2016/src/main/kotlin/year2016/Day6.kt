package year2016

import framework.Day
import util.transpose

object Day6 : Day {
    private fun String.toColumns() = lines().map { it.toList() }.transpose()

    private fun List<Char>.frequency() = groupingBy { it }.eachCount().entries.sortedBy { it.value }.map { it.key }

    override fun part1(input: String) = input.toColumns().map { it.frequency().last() }.joinToString("")

    override fun part2(input: String) = input.toColumns().map { it.frequency().first() }.joinToString("")
}