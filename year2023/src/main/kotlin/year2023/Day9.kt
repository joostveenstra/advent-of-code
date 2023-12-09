package year2023

import framework.Day
import util.allInts

object Day9 : Day<Int> {
    private fun String.toReport() = lines().map { it.allInts().toList() }

    private fun List<Int>.differences() = generateSequence(this) { it.zipWithNext { a, b -> b - a } }.takeWhile { step -> step.any { it != 0 } }

    override fun part1(input: String) = input.toReport().sumOf { line ->
        line.differences().map { it.last() }.sum()
    }

    override fun part2(input: String) = input.toReport().sumOf { line ->
        line.differences().map { it.first() }.toList().reversed().reduce { a, b -> b - a }
    }
}