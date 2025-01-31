package year2023

import framework.Day
import util.allInts

object Day9 : Day {
    private fun String.toReport() = lines().map { it.allInts().toList() }

    private fun List<Int>.extrapolate() = generateSequence(this) { it.zipWithNext { a, b -> b - a } }
        .takeWhile { step -> step.any { it != 0 } }
        .sumOf { it.last() }

    override fun part1(input: String) = input.toReport().sumOf { line -> line.extrapolate() }

    override fun part2(input: String) = input.toReport().sumOf { line -> line.reversed().extrapolate() }
}