package year2023

import framework.Context
import framework.Day
import util.allInts

class Day09(context: Context) : Day by context {
    val reports = lines.map { it.allInts().toList() }

    fun List<Int>.extrapolate() = generateSequence(this) { it.zipWithNext { a, b -> b - a } }
        .takeWhile { step -> step.any { it != 0 } }
        .sumOf { it.last() }

    fun part1() = reports.sumOf { line -> line.extrapolate() }
    fun part2() = reports.sumOf { line -> line.asReversed().extrapolate() }
}