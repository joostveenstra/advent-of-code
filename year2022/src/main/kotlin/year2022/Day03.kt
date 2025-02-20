package year2022

import framework.Context
import framework.Day

class Day03(context: Context) : Day by context {
    private fun Char.priority() = if (isUpperCase()) this - 'A' + 27 else this - 'a' + 1

    private fun List<String>.commonItem() = map { it.toSet() }.reduce { a, b -> a intersect b }.first()

    fun part1() = lines.map { it.chunked(it.length / 2) }.sumOf { it.commonItem().priority() }
    fun part2() = lines.chunked(3).sumOf { it.commonItem().priority() }
}